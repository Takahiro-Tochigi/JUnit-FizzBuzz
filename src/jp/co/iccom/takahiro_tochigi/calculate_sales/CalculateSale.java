package jp.co.iccom.takahiro_tochigi.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CalculateSale {

	public static void main(String[] args){
	HashMap<String,String> branchlist =new  HashMap<String,String>();//データを保持するmap作成
	HashMap<String,String> commoditylist = new  HashMap<String,String>();//データを保持するmap作成
	 String stBufferBR = ""; //戻り値を格納
     String stBufferCO = ""; //戻り値を格納

		try{
			File  filea =new  File("C:\\UriageFile\\branch.lst");
			FileReader fra = new FileReader(filea);
			BufferedReader bra = new BufferedReader(fra);//fileの入力
			//System.out.println(br);

			File  fileb =new  File("C:\\UriageFile\\commodity.lst");
			FileReader frb = new FileReader(fileb);
			BufferedReader brb = new BufferedReader(frb);//fileの入力

			while ((stBufferBR = bra.readLine()) != null){
				String[] namea = stBufferBR.split(",");//
				branchlist.put(namea[0],namea[1]);
			}
			while ((stBufferCO = brb.readLine())  != null){
				String[] nameb = stBufferCO.split(",");
				commoditylist.put(nameb[0],nameb[1]);
			};
			//System.out.println(branchlist);//確認のため分割した配列の表示
			//System.out.println(commoditylist);
					//後で略

			bra.close();
			brb.close();

		}catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");//ファイルの存在に関するエラー
			System.out.println(e);

		}
	}
}

