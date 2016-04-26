package jp.co.iccom.takahiro_tochigi.calculate_sales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalculateSale {
	public static void main(String[] args){
		for(int i = 0 ;i< args.length; i++){
			System.out.println(args[i]);//fileをコマンドライン引数で開く
		}
		try{
			String line;//保持する
			FileReader file = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(file);

			while ((line = br.readLine()) != null){
				String[] lines = line.split(",");
				
				if(){
					
				}
				
				for(String bl: lines){
					System.out.println(bl);//確認のため分割した配列の表示
					}
			}
			br.close();
		}catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");//ファイルの存在に関するエラー
			System.out.println(e);
		}
		
	}
}