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
		String stBufferCO = ""; //戻り値を格納


		//支店番号と支店名のハッシュマップ
		try{
			File  filea =new  File(args[0], "branch.lst");
			FileReader fra = new FileReader(filea);
			BufferedReader bra = new BufferedReader(fra);//fileの入力
			//System.out.println(bra);

			while ((stBufferBR = bra.readLine()) != null){
				String[] namea = stBufferBR.split(",");//
				branchList.put(namea[0],namea[1]);
				//全店舗分の金額の初期化、
				store.put(namea[0], 0);
			}
			bra.close();
		}catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");//ファイルの存在に関するエラー
			System.out.println(e);
		}


		try{
			File  fileb =new  File(args[0],"commodity.lst");
			FileReader frb = new FileReader(fileb);
			BufferedReader brb = new BufferedReader(frb);//fileの入力

			while ((stBufferCO = brb.readLine())  != null){
				String[] nameb = stBufferCO.split(",");
				commodityList.put(nameb[0],nameb[1]);
				product.put(nameb[0], 0);

			};
			//System.out.println(branchList);//確認のため分割した配列の表示
			//System.out.println(commoditylist);
			//後で略

			brb.close();
		}catch(IOException e){
			System.out.println("商品定義ファイルが存在しません");//ファイルの存在に関するエラー
			System.out.println(e);
		}

		//ディレクトリー内から.rcdファイル一覧の読込・・・
 		File dir = new File(args[0]);
 		File[] files = dir.listFiles();
 		for (int i = 0; i < files.length; i++) {
			if(files[i].getName().endsWith(".rcd")){
				allfile.add(files[i]);
		 		//System.out.println(allfile);
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
			break;
			//実行処理を出た時点で終了するように処理してください。
 			}
 		}
 		//allfileに入っているrcdファイルを繰り返し処理で開いていく
 		for(int i = 0; i<allfile.size(); i++){
 			ArrayList<String> extraction = new ArrayList<>();//抽出した売上ファイルを保持するリスト
 			try {
				FileReader fru =new FileReader(allfile.get(i));
				BufferedReader brf = new BufferedReader(fru);
				String extra  ;
				int nLine = 0;
				while((extra = brf.readLine())  != null){
					nLine++;
					extraction.add(extra);
					//抽出処理失敗のif文

					//4行以上の場合のエラー
					if(nLine > 3){
						System.out.println("<該当ファイル名>のフォーマットが不明です");
						break;
					}
				}
				//System.out.println(extraction);
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

				store.put(extraction.get(0),m);
				product.put(extraction.get(1),n);

				//11桁以上のの場合10桁を超えましたを表示
				int ketaA =Integer.toString(m).length();
				int ketaB =Integer.toString(n).length();
				if(10< ketaA || 10< ketaB){
					System.out.println("合計金額が10桁を超えました。");
					break;
				}
			brf.close();

			}catch (FileNotFoundException e) {

			}catch (IOException e ){
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
		public int compare(
		Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
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


		try {
			File branchOutFile = new File(args[0], "branch.out");
			FileWriter fileWriter = new FileWriter(branchOutFile);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			PrintWriter pw = new PrintWriter(bw);
			//
			for (Entry<String,Integer> s : entries) {
				pw.println( s.getKey() + ","
			+ branchList.get( s.getKey() )+","+ store.get( s.getKey() ) );
			}
			pw.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			File branchOutFile = new File(args[0], "commodity.out");
			FileWriter fileWriter = new FileWriter(branchOutFile);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			PrintWriter pw = new PrintWriter(bw);
			//
			for (Entry<String,Integer> s : commoditydown) {
				pw.println( s.getKey() + ","
			+ commodityList.get( s.getKey() )+","+ product.get( s.getKey() ) );
			}
			pw.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
 	}
}