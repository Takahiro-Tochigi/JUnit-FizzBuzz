import static org.junit.Assert.*;

import org.junit.Test;

public class FizzBazzTest {
	@Test
	public void ゼロのとき(){
		assertEquals("iの指定範囲を確認してください", FizzBazz.Answer(0));
	}
	@Test
	public void 百より大きいとき(){
		assertEquals("iの指定範囲を確認してください", FizzBazz.Answer(110));
	}
	@Test
	public void 三の倍数() {
		assertEquals("Fizz", FizzBazz.Answer(3));
	}
	@Test
	public void 五の倍数() {
		assertEquals("Buzz", FizzBazz.Answer(5));
	}
	@Test
	public void 十五の倍数() {
		assertEquals("FizzBuzz", FizzBazz.Answer(15));
	}
	@Test
	public void 近似値のチェック() {
		assertNotNull("97", FizzBazz.Answer(16));
	}

}
