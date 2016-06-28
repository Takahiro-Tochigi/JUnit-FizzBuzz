
public class FizzBazz {

	public static void main(String args[]){

		for (int i = 1 ; i<=100 ; i++){
			System.out.println(Answer(i));
		}
	}

	public static String Answer (int i){
		String resultMessage = null;

		if(i==0 || i>100){
			resultMessage = "iの指定範囲を確認してください";
		}else if(i%3==0 && i%5==0){
			resultMessage = "FizzBuzz";
		}else if(i%5==0){
			resultMessage = "Buzz";
		}else if(i%3==0 ){
			resultMessage = "Fizz";
		}else{
			resultMessage =  String.valueOf(i);
		}
		return resultMessage;
	}
}
