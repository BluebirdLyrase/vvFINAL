package UnitTest;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import BookStore.*;

@RunWith(Parameterized.class)
public class BookStoreTest {
	

	BookStore bookStore;
//	ArrayList<Integer> customer_type = new ArrayList<Integer>();
//	ArrayList<Integer> order_price = new ArrayList<Integer>();
//	ArrayList<Integer> coupon_discount = new ArrayList<Integer>();
//	ArrayList<Integer> total_discount = new ArrayList<Integer>();
//	ArrayList<Double> grand_price = new ArrayList<Double>();
	
	int customer_type = 0;
	int order_price = 0;
	int coupon_discount = 0;
	int total_discount = 0;
	double grand_price = 0;
	int actualDiscount;
	
	public BookStoreTest(int customer_type, int order_price, int coupon_discount, int total_discount,
			double grand_price) {
		super();
		this.customer_type = customer_type;
		this.order_price = order_price;
		this.coupon_discount = coupon_discount;
		this.total_discount = total_discount;
		this.grand_price = grand_price;
	}

	@Before
	public void setUp() throws Exception {
		bookStore = new BookStore();
	}

	@After
	public void tearDown() throws Exception {
		bookStore = null;
	}
	

		
	@Parameters
    public static List<Object[]> data() {
    List<Object[]> list = new ArrayList<Object[]>();
	String csvFile = "NewTestCase.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int cus_type = 0;
	
		try {

			br = new BufferedReader(new FileReader(csvFile));
			boolean isfirst = true;
			while ((line = br.readLine()) != null) {
				if (isfirst) {
					isfirst = false;
				} else {
					String[] testcase = line.split(cvsSplitBy);
					switch (testcase[2]) {
					case "Bronze":
						 cus_type = 1;
						break;
					case "Silver":
						 cus_type = 2;
						break;
					case "Gold":
						 cus_type = 3;
						break;
					default:
						 cus_type = 999;
						break;
					}

					int order_price = Integer.parseInt(testcase[5]);
					int coupon_discount = Integer.parseInt(testcase[7]);
					int total_discount = Integer.parseInt(testcase[8]);
					double grand_price = Double.parseDouble(testcase[9]);
					list.add(new Object[] { cus_type,order_price,coupon_discount,total_discount,grand_price});
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
        

        return list;
    }
	@Test
	public void testGetDiscount() {
			int expectedDiscount = total_discount ;
			actualDiscount = BookStore.getDiscount(order_price, customer_type, coupon_discount);
			System.out.println("Customer:"+customer_type+" order_price:"+order_price+"  coupon_discount:"+coupon_discount);
			System.out.println("total_discount:"+total_discount+" actual_dis:"+actualDiscount);
			assertEquals("Error: Expected is not equal to Actual", expectedDiscount, actualDiscount);
	}

	@Test
	public void testGetDiscountBalance() {
			bookStore.addShoppingCart(0, null, null);
			bookStore.addCartDetail(0, null, null, order_price);
			double actualNetBal = bookStore.getDiscountBalance(0, total_discount);
			assertEquals("Error: Expected is not equal to Actual", grand_price, actualNetBal, 0.0);
	}
	
	@Test
		public void testPerchase() {
			bookStore.addShoppingCart(0, null, null);
			bookStore.addCartDetail(0, null, null, order_price);
			int discount = BookStore.getDiscount(order_price, customer_type, coupon_discount);
//			double paid = 10000.0; 	
//			double expectedChange = 0.0;
			bookStore.purchase(0, paid, discount);
//			assertEquals()
	}
	
	

}
