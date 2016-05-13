package jp.co.iccom.takahiro_tochigi.calculate_sales;

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
public class CalculateSale {

	public static void main(String[] args){
		if (args.length != 1) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//引数がないとき、複数あるときのエラー処理を作る
		HashMap<String,String> branchList =new  HashMap<String,String>();//データを保持するmap作成
		HashMap<String,String> commodityList = new  HashMap<String,String>();//データを保持するmap作成
		ArrayList<File> allrcdFile = new ArrayList<File>();//全ての.rcdファイルのリストを保持する
		HashMap<String,Integer> branchEarnings = new HashMap<>();//支店の売上合計を保持する
		HashMap<String,Integer> commodityEarnings = new HashMap<>();//商品の売上合計を保持する
		String stringBufferedBranch = ""; //戻り値を格納
		String stringBufferedCommodity = ""; //戻り値を格

		try{
			//支店番号と支店名のハッシュマップ
			File  fileBranch =new  File(args[0], "branch.lst");
			FileReader fileReadBranch = new FileReader(fileBranch);
			BufferedReader bufferedReaderBranch = new BufferedReader(fileReadBranch);//fileの入力
			try{
				while (( stringBufferedBranch = bufferedReaderBranch.readLine()) != null){
					String[] branchContents = stringBufferedBranch.split(",");
					if(branchContents[0].matches("^[0-9]{3}$")){
						branchList.put(branchContents[0],branchContents[1]);
						//全店舗分の金額の初期化、
						branchEarnings.put(branchContents[0], 0);
					}else{
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return;
					}
				}
			}catch(IOException e){
				System.out.println("支店定義ファイルが存在しません");//ファイルの存在に関するエラー
				System.out.println(e);
			}finally{
				bufferedReaderBranch.close();
			}
			File  fileCommodity =new  File(args[0],"commodity.lst");
			FileReader fileReaderCommodity = new FileReader(fileCommodity);
			BufferedReader bufferedReaderCommodity = new BufferedReader(fileReaderCommodity);//fileの入力
			try{
				while (( stringBufferedCommodity = bufferedReaderCommodity.readLine())  != null){
					String[]commodityContents =  stringBufferedCommodity.split(",");
					if(commodityContents[0].matches("^[A-Z0-9]{8}$")){
						commodityList.put(commodityContents[0],commodityContents[1]);
						//全店舗分の金額の初期化、
						commodityEarnings.put(commodityContents[0], 0);
					}else{
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return;
					}
				};
			}catch(IOException e){
				System.out.println("商品定義ファイルが存在しません");//ファイルの存在に関するエラー
				System.out.println(e);
			}finally{
				bufferedReaderCommodity.close();
			}
			//ディレクトリー内から.rcdファイル一覧の読込・・・
	 		File directry = new File(args[0]);
	 		File[] files = directry.listFiles();
	 		for (int i = 0; i < files.length; i++) {
				if(files[i].getName().endsWith(".rcd")){
					allrcdFile.add(files[i]);
			 	}
			}
	 		// 連番確認
	 		ArrayList<Integer> numberfile = new ArrayList<Integer>();
	 		for (int i = 0; i < allrcdFile.size(); i++) {
	 			String fileName = allrcdFile.get(i).getName();
	 			String number = fileName.split("\\.")[0];
	 			int intNumber = Integer.parseInt(number);
	 			//System.out.println(Integer.parseInt(allfile.get(i).getName().split("\\.")[0]));
	 			numberfile.add(intNumber);
	 			//System.out.println(numberfile);
	 			if(!(numberfile.size() == numberfile.get(numberfile.size() - 1))){
				System.out.println("売上ファイルが連番になっていません。");
				return;
	 			}
	 		}
	 		//allfileに入っているrcdファイルを繰り返し処理で開いていく
	 		for(int i = 0; i<allrcdFile.size(); i++){
	 			ArrayList<String> extraction = new ArrayList<>();//抽出した売上ファイルを保持するリスト
					FileReader fileReaderRcd = new FileReader( allrcdFile.get(i) );
					BufferedReader bufferedReaderRcd = new BufferedReader( fileReaderRcd );
					String extra  ;
				try{
					while((extra = bufferedReaderRcd.readLine())  != null){
						extraction.add(extra);
					}
					//4行以上の場合のエラー
					if(extraction.size() != 3){
						System.out.println(files[i]+"のフォーマットが不正です");
						return;
					}
					if (!branchList.containsKey(extraction.get(0))){
						System.out.println(files[i]+"の支店コードが不正です");
						return;
					}
					if (!commodityList.containsKey(extraction.get(1))){
						System.out.println(files[i]+"の商品コードが不正です");
						return;
					}
					// 集計処理
					System.out.println(extraction);
					// 足したい値を取得
					int extraprice = Integer.parseInt(extraction.get(2));
					//System.out.println(e);
					// 既存の値を取得
					int branchAggregatedPrice = branchEarnings.get(extraction.get(0));
					int commodityAggregatedPrice = commodityEarnings.get(extraction.get(1));
					// 合計　=　足したい値　+　既存の値
					int totalFeeBranch = extraprice + branchAggregatedPrice;
					int totalFeeCommodity = extraprice + commodityAggregatedPrice;
					//合計を格納
					branchEarnings.put( extraction.get(0) , totalFeeBranch );
					commodityEarnings.put( extraction.get(1) , totalFeeCommodity );
					//11桁以上のの場合10桁を超えましたを表示
					int ketaA =Integer.toString(totalFeeBranch).length();
					int ketaB =Integer.toString(totalFeeCommodity).length();
					if(10 < ketaA || 10 < ketaB){
						System.out.println("合計金額が10桁を超えました。");
						break;
					}
				}catch(FileNotFoundException e) {
					System.out.println("予期せぬエラーが発生しました");
				}catch(IOException e ){
					System.out.println("予期せぬエラーが発生しました");
				}finally{
					bufferedReaderRcd.close();
				}
		 	}
			//支店別集計ファイル　降順
			List<Map.Entry<String,Integer>> branchDown =
						new ArrayList<Map.Entry<String,Integer>>(branchEarnings.entrySet());
			Collections.sort(branchDown, new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
					return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
				}
			});
			//商品別集計ファイル　降順
			List<Map.Entry<String,Integer>> commodityDown =
						new ArrayList<Map.Entry<String,Integer>>(commodityEarnings.entrySet());
			Collections.sort(  commodityDown, new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
					return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
				}
			});
			File branchOutFile = new File(args[0], "branch.out");
			FileWriter fileWriterBranch = new FileWriter(branchOutFile);
			BufferedWriter bufferedWriterBranch = new BufferedWriter(fileWriterBranch);
			PrintWriter printWriterBranch = new PrintWriter(bufferedWriterBranch);
			//
			try{
				for (Entry<String,Integer> s : branchDown) {
					printWriterBranch.println( s.getKey() + ","
							+ branchList.get( s.getKey() )+","+ branchEarnings.get( s.getKey() ) );
				}
			}finally{
				printWriterBranch.close();
			}
			File commodityOutFile = new File(args[0], "commodity.out");
			FileWriter fileWriterCommodity = new FileWriter(commodityOutFile);
			BufferedWriter bufferWriterCommodity = new BufferedWriter(fileWriterCommodity);
			PrintWriter printWriteCommodity = new PrintWriter(bufferWriterCommodity);
			try{
				for (Entry<String,Integer> s : commodityDown) {
					printWriteCommodity.println( s.getKey() + ","
							+ commodityList.get( s.getKey() )+","+ commodityEarnings.get( s.getKey() ) );
				}
			}finally{
				printWriteCommodity.close();
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			System.out.println(e);
		}
	}
}