package jp.co.iccom.takahiro_tochigi.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CalculateSale {

	public static void main(String[] args){

		HashMap<String,String> branchlist =new  HashMap<String,String>();//データを保持するmap作成
		HashMap<String,String> commoditylist = new  HashMap<String,String>();//データを保持するmap作成
 		ArrayList<File> allfile = new ArrayList<File>();//売上ファイルのリストを保持するためのリスト
 

		String stBufferBR = ""; //戻り値を格納
		String stBufferCO = ""; //戻り値を格納



		try{
			File  filea =new  File(args[0], "branch.lst");
			FileReader fra = new FileReader(filea);
			BufferedReader bra = new BufferedReader(fra);//fileの入力
			//System.out.println(bra);

			while ((stBufferBR = bra.readLine()) != null){
				String[] namea = stBufferBR.split(",");//
				branchlist.put(namea[0],namea[1]);
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
				commoditylist.put(nameb[0],nameb[1]);
			};
			//System.out.println(branchlist);//確認のため分割した配列の表示
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
 			int inumber = Integer.parseInt(number);
 			//System.out.println(Integer.parseInt(allfile.get(i).getName().split("\\.")[0]));
 			numberfile.add(inumber);
 			//System.out.println(numberfile);

 			if(numberfile.size() == numberfile.get(numberfile.size() - 1)){

 			}else{
 			System.out.println("売上ファイルが連番になっていません。");
 			//実行処理を出た時点で終了するように処理してください。
 			}
 		}
 		//allfileに入っているrcdファイルを繰り返し処理で開いていく

 		for(int i = 0; i<allfile.size(); i++){
 			ArrayList<String> extraction = new ArrayList<>();
 			try {
				FileReader fru =new FileReader(allfile.get(i));
				BufferedReader brf = new BufferedReader(fru);
				String extra  ;
				while((extra = brf.readLine())  != null){
					extraction.add(extra);
					//抽出処理失敗のif文
				}
				//System.out.println(extraction);
				// 集計
				HashMap<String,Integer> store =new HashMap<>();
				int money= new Integer(extraction.get(2)).intValue();//String型をint型に
				String storeNumber =extraction.get(0);
				
				store.put(storeNumber, money);
				
				
			brf.close();
			}catch (FileNotFoundException e) {

			}catch (IOException e ){

			}
 		}
 	}

}


