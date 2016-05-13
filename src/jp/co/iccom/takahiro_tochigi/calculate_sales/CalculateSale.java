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

		HashMap<String,String> branchList =new  HashMap<String,String>();//データを保持するmap作成
		HashMap<String,String> commodityList = new  HashMap<String,String>();//データを保持するmap作成
		ArrayList<File> allfile = new ArrayList<File>();//売上ファイルのリストを保持するためのリスト
		HashMap<String,Integer> store = new HashMap<>();//支店の売上合計を保持する
		HashMap<String,Integer> product = new HashMap<>();//商品の売上合計を保持する
		String stBufferBR = ""; //戻り値を格納
		String stBufferCO = ""; //戻り値を格
		try{
			//支店番号と支店名のハッシュマップ
			File  filea =new  File(args[0], "branch.lst");
			FileReader fra = new FileReader(filea);
			BufferedReader bra = new BufferedReader(fra);//fileの入力
			try{
				while ((stBufferBR = bra.readLine()) != null){
					String[] namea = stBufferBR.split(",");//
					if(namea[0].matches("^[0-9]{3}$")){
						branchList.put(namea[0],namea[1]);
						//全店舗分の金額の初期化、
						store.put(namea[0], 0);
					} else{
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return;
					}
				}
			}catch(IOException e){
				System.out.println("支店定義ファイルが存在しません");//ファイルの存在に関するエラー
				System.out.println(e);

			}finally{
				bra.close();
			}
			File  fileb =new  File(args[0],"commodity.lst");
			FileReader frb = new FileReader(fileb);
			BufferedReader brb = new BufferedReader(frb);//fileの入力
			try{
				while ((stBufferCO = brb.readLine())  != null){
					String[] nameb = stBufferCO.split(",");
					if(nameb[0].matches("^[A-Z0-9]{8}$")){
						commodityList.put(nameb[0],nameb[1]);
						//全店舗分の金額の初期化、
						product.put(nameb[0], 0);
					} else{
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return;
					}
				};
				//System.out.println(branchList);//確認のため分割した配列の表示
				//System.out.println(commoditylist);
				//後で略
			}catch(IOException e){
				System.out.println("商品定義ファイルが存在しません");//ファイルの存在に関するエラー
				System.out.println(e);
			}finally{
				brb.close();
			}

			//ディレクトリー内から.rcdファイル一覧の読込・・・
	 		File dir = new File(args[0]);
	 		File[] files = dir.listFiles();
	 		for (int i = 0; i < files.length; i++) {
				if(files[i].getName().endsWith(".rcd")){
					allfile.add(files[i]);
			 	}
			}//
	 		// 連番確認
	 		ArrayList<Integer> numberfile = new ArrayList<Integer>();
	 		for (int i = 0; i < allfile.size(); i++) {
	 			String fileName = allfile.get(i).getName();
	 			String number = fileName.split("\\.")[0];
	 			int intNumber = Integer.parseInt(number);
	 			//System.out.println(Integer.parseInt(allfile.get(i).getName().split("\\.")[0]));
	 			numberfile.add(intNumber);
	 			//System.out.println(numberfile);
	 			if(numberfile.size() == numberfile.get(numberfile.size() - 1)){
				}else{
				System.out.println("売上ファイルが連番になっていません。");
				return;
	 			}
	 		}
	 		//allfileに入っているrcdファイルを繰り返し処理で開いていく
	 		for(int i = 0; i<allfile.size(); i++){
	 			ArrayList<String> extraction = new ArrayList<>();//抽出した売上ファイルを保持するリスト
					FileReader fru =new FileReader(allfile.get(i));
					BufferedReader brf = new BufferedReader(fru);
					String extra  ;
				try{
					while((extra = brf.readLine())  != null){
						extraction.add(extra);
						//抽出処理失敗のif文
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
						System.out.println(files[i]+"のフォーマットが不正です");
						return;
					}
					// 集計処理

					// 足したい値を取得
					int e = Integer.parseInt(extraction.get(2));
					//System.out.println(e);

					// 既存の値を取得
					int s = store.get(extraction.get(0));
					int p = product.get(extraction.get(1));

					// 合計　=　足したい値　+　既存の値
					int m = e + s;
					int n = e + p;
					//合計を格納
					//if文？

					store.put(extraction.get(0),m);
					product.put(extraction.get(1),n);

					//11桁以上のの場合10桁を超えましたを表示
					int ketaA =Integer.toString(m).length();
					int ketaB =Integer.toString(n).length();
					if(10< ketaA || 10< ketaB){
						System.out.println("合計金額が10桁を超えました。");
						break;
						}

				}catch (FileNotFoundException e) {
					System.out.println("予期せぬエラーが発生しました");

				}catch (IOException e ){
					System.out.println("予期せぬエラーが発生しました");

				}finally{
					brf.close();
				}
		 	}
			//支店別集計ファイル　降順
			List<Map.Entry<String,Integer>> entries =
						new ArrayList<Map.Entry<String,Integer>>(store.entrySet());

			Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
						return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
				}
			});
			/*for (Entry<String,Integer> s : entries) {
				System.out.println("s.getKey() : " + s.getKey());
				System.out.println("s.getValue() : " + s.getValue());
			}*/
			//System.out.println(entries);
			//

			//商品別集計ファイル　降順
			List<Map.Entry<String,Integer>> commoditydown =
						new ArrayList<Map.Entry<String,Integer>>(product.entrySet());
			Collections.sort( commoditydown, new Comparator<Map.Entry<String,Integer>>() {
				@Override
				public int compare(Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
						return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
				}
			});

			//支店別売上集計ファイルに出力するためのレイアウト
			/* for(String branchKey :branchList.keySet()){
				 System.out.println( branchKey + ","
			 + branchList.get(branchKey)+","+ store.get(branchKey));
	         }
			 //商品別売上集計ファイルに出力するためのレイアウト
			 for(String commodityKey :commodityList.keySet()){
	             System.out.println( commodityKey + ","
			 + commodityList.get(commodityKey)+","+ product.get(commodityKey));
	         }*/
			//System.out.println(product);

			//作成したファイルに集計ファイルを合計金額の降順で出力

			File branchOutFile = new File(args[0], "branch.out");
			FileWriter fileWriterBranch = new FileWriter(branchOutFile);
			BufferedWriter bwBranch = new BufferedWriter(fileWriterBranch);
			PrintWriter pwBranch = new PrintWriter(bwBranch);
			//
			try{
				for (Entry<String,Integer> s : entries) {
					pwBranch.println( s.getKey() + ","
							+ branchList.get( s.getKey() )+","+ store.get( s.getKey() ) );
				}
			}finally{
				pwBranch.close();
			}
			File commodityOutFile = new File(args[0], "commodity.out");
			FileWriter fileWriterCommodity = new FileWriter(commodityOutFile);
			BufferedWriter bwCommodity = new BufferedWriter(fileWriterCommodity);
			PrintWriter pwCommodity = new PrintWriter(bwCommodity);
			for (Entry<String,Integer> s : commoditydown) {
			pwCommodity.println( s.getKey() + ","
					+ commodityList.get( s.getKey() )+","+ product.get( s.getKey() ) );
			}
			pwCommodity.close();
		}catch(IOException e){
			System.out.println("予期せぬエラーが発生しました");
			System.out.println(e);
		}
	}
}