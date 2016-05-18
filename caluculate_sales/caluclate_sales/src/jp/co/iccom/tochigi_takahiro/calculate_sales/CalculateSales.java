package jp.co.iccom.tochigi_takahiro.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CalculateSales{
	//ファイルの読込メソッド
	public static boolean fileLoad(String commandLineArguments, String definitionFile, String regularExpression,
			HashMap<String, String> definitionList, HashMap<String, Long> saleResultList, String fileName){
		String stringBuffered = "";

		try{
			File  file = new  File(commandLineArguments, definitionFile);
			if( file.exists() ){
				FileReader fr = new FileReader(file);
				BufferedReader br = null;
				try{
					br = new BufferedReader(fr);
					while (( stringBuffered = br.readLine()) != null){
					String[] contents = stringBuffered.split(",");
					if( contents[0].matches(regularExpression) && contents.length == 2){
							definitionList.put(contents[0], contents[1]);
							//全店舗分の金額の初期化、
							saleResultList.put(contents[0], (long) 0);
						}else{
							System.out.println(fileName + "定義ファイルのフォーマットが不正です");
							return false;
						}
					}
				}catch(IOException e){
					System.out.println(e);
					return false;
				}finally{
					br.close();
				}
			}else{
				System.out.println(fileName + "定義ファイルが存在しません");
				return false;
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}
		return true;
	}

	//ファイルの出力メソッド
	public static boolean outPut(String commandLineArguments, String outPutFile, List<Entry<String,Long>>  descendingOfAggregateData
			,HashMap<String, String> definitionList,HashMap<String, Long> saleResultList){
		try{
			File outFile = new File( commandLineArguments, outPutFile);
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = null;
			try{
				bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				for (Entry<String,Long> s : descendingOfAggregateData) {
					pw.println( s.getKey() + ","
							+ definitionList.get( s.getKey() )+","+ saleResultList.get( s.getKey() ) );
				}
			}finally{
				bw.close();
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}
		return true;
	}

	//mainメソッド
	public static void main(String[] args){
		if (args.length != 1) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//引数がないとき、複数あるときのエラー処理を作る
		HashMap<String, String> branchList =new  HashMap<String, String>();//データを保持するmap作成
		HashMap<String, String> commodityList = new  HashMap<String, String>();//データを保持するmap作成
		ArrayList<File> allrcdFile = new ArrayList<File>();//全ての.rcdファイルのリストを保持する
		HashMap<String, Long> branchEarnings = new HashMap<>();//支店の売上合計を保持する
		HashMap<String, Long> commodityEarnings = new HashMap<>();//商品の売上合計を保持する

		if(!fileLoad(args[0], "branch.lst","^[0-9]{3}$", branchList,branchEarnings, "支店")){
			return;
		}

		if(!fileLoad(args[0], "commodity.lst","^[A-Za-z0-9]{8}$", commodityList,commodityEarnings, "商品")){
			return;
		}

		try{
			//ディレクトリー内から.rcdファイル一覧の読込・・・
			File directry = new File(args[0]);
			File[] files = directry.listFiles();
			for ( int i = 0; i < files.length; i++ ){
				if(files[i].getName().endsWith(".rcd")){
					if(files[i].isFile()){
						allrcdFile.add(files[i]);
					}
				}
			}

			// 連番確認
			ArrayList<Long> numberfile = new ArrayList<Long>();
			for (int i = 0; i < allrcdFile.size(); i++) {
				String fileName = allrcdFile.get(i).getName();
				String number = fileName.split("\\.")[0];
				long intNumber = Long.parseLong(number);
				numberfile.add(intNumber);
				if(!(numberfile.size() == numberfile.get(numberfile.size() - 1))){
					System.out.println("売上ファイル名が連番になっていません");
					return;
				}
			}

			for(int i = 0; i<allrcdFile.size(); i++){
				ArrayList<String> extraction = new ArrayList<>();//抽出した売上ファイルを保持するリスト
				FileReader fileReaderRcd = new FileReader( allrcdFile.get(i) );
				BufferedReader bufferedReaderRcd = new BufferedReader( fileReaderRcd );
				String extra;
				try{
					while((extra = bufferedReaderRcd.readLine())  != null){
						extraction.add(extra);
					}
					if(extraction.size() != 3){
						System.out.println(files[i].getName()+"のフォーマットが不正です");
						return;
					}
					if ( !branchList.containsKey( extraction.get(0) ) ){
						System.out.println(files[i].getName()+"の支店コードが不正です");
						return;
					}
					if ( !commodityList.containsKey(extraction.get(1) ) ){
						System.out.println(files[i].getName()+"の商品コードが不正です");
						return;
					}
					// 集計処理
					// 足したい値を取得
					long extraprice = Long.parseLong( extraction.get(2) );
					// 既存の値を取得
					long branchAggregatedPrice = branchEarnings.get( extraction.get(0) );
					long commodityAggregatedPrice = commodityEarnings.get( extraction.get(1) );
					// 合計 = 足したい値 + 既存の値
					long totalFeeBranch = extraprice + branchAggregatedPrice;
					long totalFeeCommodity = extraprice + commodityAggregatedPrice;
					//合計を格納
					branchEarnings.put( extraction.get(0), totalFeeBranch );
					commodityEarnings.put( extraction.get(1), totalFeeCommodity );
					//11ig桁以上のの場合10桁を超えましたを表示
					int fureLengthBranch =  String.valueOf(totalFeeBranch).length();
					int fureLengthCommodity =  String.valueOf(totalFeeCommodity).length();
					if(10 < fureLengthBranch || 10 < fureLengthCommodity ){
						System.out.println("合計金額が10桁を超えました");
						return;
					}
				}catch(FileNotFoundException e) {
					System.out.println("予期せぬエラーが発生しました");
				}catch(IOException e ){
					System.out.println("予期せぬエラーが発生しました");
				}finally{
					bufferedReaderRcd.close();
				}
			}

			//支店別集計ファイル 降順
			List<Map.Entry<String, Long>> branchDown =
					new ArrayList<Map.Entry<String, Long>>(branchEarnings.entrySet());
			Collections.sort(branchDown, new Comparator<Map.Entry<String, Long>>() {
				@Override
				public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});
			//商品別集計ファイル 降順
			List<Map.Entry<String, Long>> commodityDown =
					new ArrayList<Map.Entry<String, Long>>(commodityEarnings.entrySet());
			Collections.sort(  commodityDown, new Comparator<Map.Entry<String, Long>>() {
				@Override
				public int compare(Entry<String ,Long> entry1, Entry<String, Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});

			//出力をメソッド分け
			if(!outPut(args[0], "branch.out", branchDown, branchList, branchEarnings)){
				return;
			}
			if(!outPut(args[0], "commodity.out", commodityDown, commodityList, commodityEarnings)){
				return;
			}

		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
		}
	}
}