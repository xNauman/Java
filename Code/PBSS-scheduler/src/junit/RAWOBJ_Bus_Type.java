/**
 * 
 */
package junit;

import static org.junit.Assert.assertEquals;

//import java.awt.image.BufferedImage;

import org.junit.Test;

//import rawobjects.ImageSerializer;
/**
 * @author RAJA NOMAN & JOSE VARAS
 *
 */
public class RAWOBJ_Bus_Type {

	rawobjects.Bus_Type mBusType = new rawobjects.Bus_Type();
	
	/**
	 * setting a specific color of the bus and then checking.
	 */

    
	@Test
	public void getBusTypeIDTest(){
		mBusType.setBusTypeID(3);
		
		assertEquals(3,mBusType.getBusTypeID());
	}
    
	@Test
	public void getBt_HeightTest(){
		mBusType.setBt_Height(53);
		
		assertEquals(53,mBusType.getBt_Height());
	}

	@Test
	public void getBt_WidthTest(){
		mBusType.setBt_Width(33);
		
		assertEquals(33,mBusType.getBt_Width());
	}
 
    
	@Test
	public void getBusColourTest(){
		mBusType.setBusColour(12);
		
		assertEquals(12,mBusType.getBusColour());
	}

	/**
	 * setting the bus type and checking it.
	 */
	@Test
	public void getBusTypeTest(){
		
		mBusType.setBusType("A");
		assertEquals("A",this.mBusType.getBusType());
	}
	/**
	 *checking bus max capacity after setting it manually. 
	 */
	
	@Test
	public void getMaxCapacityTest(){
		this.mBusType.setMaxCapacity((short) 100);
		assertEquals(100, mBusType.getMaxCapacity());
		
	}
	
	/*
	     public BufferedImage getBusImage(){
        return ImageSerializer.intArrayToBufferedImage(BusImage, Bt_Width, Bt_Height);
    }
    
    public void setBusImage(BufferedImage pBusImage){
        BusImage = ImageSerializer.serialiseBufferedImage(pBusImage);
    }
	 */
	 

}
