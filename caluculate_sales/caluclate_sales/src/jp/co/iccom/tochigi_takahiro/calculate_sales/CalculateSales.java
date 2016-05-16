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

class FileRead {

}

public class CalculateSales{
	public static void main(String[] args){
		if (args.length != 1) {
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//引数がないとき、複数あるときのエラー処理を作る
		HashMap<String,String> branchList =new  HashMap<String,String>();//データを保持するmap作成
		HashMap<String,String> commodityList = new  HashMap<String,String>();//データを保持するmap作成
		ArrayList<File> allrcdFile = new ArrayList<File>();//全ての.rcdファイルのリストを保持する
		HashMap<String,Long> branchEarnings = new HashMap<>();//支店の売上合計を保持する
		HashMap<String,Long> commodityEarnings = new HashMap<>();//商品の売上合計を保持する
		String stringBufferedBranch = ""; //戻り値を格納
		String stringBufferedCommodity = ""; //戻り値を格納

		try{
			//支店番号と支店名のハッシュマップ
			File  fileBranch = new  File(args[0], "branch.lst");
			if(fileBranch.exists()){
				FileReader fileReadBranch = new FileReader(fileBranch);
				BufferedReader bufferedReaderBranch = new BufferedReader(fileReadBranch);//fileの入力
				try{
					while (( stringBufferedBranch = bufferedReaderBranch.readLine()) != null){
						String[] branchContents = stringBufferedBranch.split(",");
						if(branchContents[0].matches("^[0-9]{3}$")){
							if(branchContents.length == 2){
								branchList.put(branchContents[0],branchContents[1]);
								//全店舗分の金額の初期化、
								branchEarnings.put(branchContents[0], (long) 0);
							}else{
								System.out.println("支店定義ファイルのフォーマットが不正です");
								return;
							}
						}else{
							System.out.println("支店定義ファイルのフォーマットが不正です");
							return;
						}
					}
				}catch(IOException e){
					System.out.println(e);
				}finally{
					bufferedReaderBranch.close();
				}
			}else{
				System.out.println("支店定義ファイルが存在しません");
				return;
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
		}
		try{
			File  fileCommodity =new  File(args[0],"commodity.lst");
			if(fileCommodity.exists()){
				FileReader fileReaderCommodity = new FileReader(fileCommodity);
				BufferedReader bufferedReaderCommodity = new BufferedReader(fileReaderCommodity);//fileの入力
				try{
					while (( stringBufferedCommodity = bufferedReaderCommodity.readLine())  != null){
						String[]commodityContents =  stringBufferedCommodity.split(",");
						if(commodityContents.length == 2){
							commodityList.put(commodityContents[0],commodityContents[1]);
								if(commodityContents[0].matches("^[A-Za-z0-9]{8}$")){
									commodityList.put(commodityContents[0],commodityContents[1]);
									//全商品分の金額の初期化、
									commodityEarnings.put( commodityContents[0] , (long) 0);
								}else{
									System.out.println("商品定義ファイルのフォーマットが不正です");
									return;
								}
						}else{
							System.out.println("商品定義ファイルのフォーマットが不正です");
							return;
						}
					};
				}catch(IOException e){
					System.out.println("予期せぬエラーが発生しました");
				}finally{
					bufferedReaderCommodity.close();
				}
			}else{
				System.out.println("商品定義ファイルが存在しません");
				return;
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
		}

		try{
			//ディレクトリー内から.rcdファイル一覧の読込・・・
			File directry = new File(args[0]);
			File[] files = directry.listFiles();
			for ( int i = 0; i < files.length; i++ ) {
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
				String extra  ;
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
					branchEarnings.put( extraction.get(0) , totalFeeBranch );
					commodityEarnings.put( extraction.get(1) , totalFeeCommodity );
					//11桁以上のの場合10桁を超えましたを表示
					int ketaA =  String.valueOf(totalFeeBranch).length();
					int ketaB =  String.valueOf(totalFeeCommodity).length();
					if(10 < ketaA || 10 < ketaB){
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
			List<Map.Entry<String,Long>> branchDown =
					new ArrayList<Map.Entry<String,Long>>(branchEarnings.entrySet());
			Collections.sort(branchDown, new Comparator<Map.Entry<String,Long>>() {
				@Override
				public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});
			//商品別集計ファイル 降順
			List<Map.Entry<String,Long>> commodityDown =
					new ArrayList<Map.Entry<String,Long>>(commodityEarnings.entrySet());
			Collections.sort(  commodityDown, new Comparator<Map.Entry<String,Long>>() {
				@Override
				public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2) {
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});
			File branchOutFile = new File(args[0], "branch.out");
			FileWriter fileWriterBranch = new FileWriter(branchOutFile);
			BufferedWriter bufferedWriterBranch = new BufferedWriter(fileWriterBranch);
			PrintWriter printWriterBranch = new PrintWriter(bufferedWriterBranch);
			//
			try{
				for (Entry<String,Long> s : branchDown) {
					printWriterBranch.println( s.getKey() + ","
							+ branchList.get( s.getKey() )+","+ branchEarnings.get( s.getKey() ) );
				}
			}finally{
				printWriterBranch.close();
			}
			File commodityOutFile = new File(args[0], "commodity.out");
			FileWriter fileWriterCommodity = new FileWriter( commodityOutFile) ;
			BufferedWriter bufferWriterCommodity = new BufferedWriter( fileWriterCommodity );
			PrintWriter printWriteCommodity = new PrintWriter( bufferWriterCommodity );
			try{
				for (Entry<String,Long> s : commodityDown ) {
					printWriteCommodity.println( s.getKey() + ","
							+ commodityList.get( s.getKey() )+","+ commodityEarnings.get( s.getKey() ) );
				}
			}finally{
				printWriteCommodity.close();
			}
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
		}
	}
}