import java.awt.*;
import javax.swing.*;

public class Mountain extends JFrame{
	static final int MAX=256;
	static final int screenWidth  = 1500;
	static final int screenHeight = 900;
	static double landscape[][] = new double[MAX+1][MAX+1];
	// ----------------------------------------------------------------------
	// --- generateLandscape                                              ---
	// --- generates a natural landscape                                  ---
	// --- @param x1,y1,x2,y2 are the koordinates in which the landscape  ---
	// ---        is generated                                            ---
	// --- @param deviation is the maximum lift of each point according   ---
	// ---        the mean value of its neighbor points                   ---
	// ----------------------------------------------------------------------
	int rand(){
		return (int)(Math.random()*1000000.0);
	}
	void genLandscape(int x1,int y1, int x2, int y2, int deviation)
	{
	 int x = x1+(x2-x1)/2;
	 int y = y1+(y2-y1)/2;

	 deviation=deviation/2;
	 if (deviation<1){
		 deviation=1;
	 }

	 if (y2-y1>1 && x2-x1>1){

		 if (landscape[x][y]==-1000){
			 landscape[x][y]=(landscape[x1][y1]+(landscape[x2][y2]-landscape[x1][y1])/2)+
							   (rand()%deviation-deviation/2);
		 }
		 if (landscape[x1][y]==-1000){
			 landscape[x1][y]=(landscape[x1][y1]+(landscape[x1][y2]-landscape[x1][y1])/2)+
							   (rand()%deviation-deviation/2);
		 }
		 if (landscape[x][y1]==-1000){
			 landscape[x][y1]=(landscape[x1][y1]+(landscape[x2][y1]-landscape[x1][y1])/2)+
							   (rand()%deviation-deviation/2);
		 }
		 if (landscape[x][y2]==-1000){
			 landscape[x][y2]=(landscape[x1][y2]+(landscape[x2][y2]-landscape[x1][y2])/2)+
							   (rand()%deviation-deviation/2);
		 }
		 if (landscape[x2][y]==-1000){
			 landscape[x2][y]=(landscape[x2][y1]+(landscape[x2][y2]-landscape[x2][y1])/2)+
							   (rand()%deviation-deviation/2);
		 }

		 genLandscape(x1,y1,x,y,deviation);
		 genLandscape(x,y,x2,y2,deviation);
		 genLandscape(x1,y,x,y2,deviation);
		 genLandscape(x,y1,x2,y,deviation);

	 }

	}
	// ----------------------------------------------------------------------
	// --- drawLandscape                                                  ---
	// --- draws te landscape on the screen
	// ---                                                                ---
	// --- @param zz is the shift of the landscape into the screen        ---
	// ----------------------------------------------------------------------
	void drawLandscape(Graphics g, double zz)
	{
		int x,z;

		// everything under 0 is water = 0 !!!
		for (x=0;x<MAX;x++){
			for (z=0;z<MAX;z++){
				if (landscape[x][z]<0){
					landscape[x][z]=0;
				}
			}
		}

		g.fillRect(0, 0, screenWidth,screenHeight);
		
		for (z=MAX-1;z>0;z--){
			for (x=0;x<MAX;x++){
						
				int xx = screenWidth/2+(int)(( (x-64)*4 )/((z*4+200+zz)*0.001));
				int yy = 100-(int)(( landscape[x][z]-200 )/((z*4+200+zz)*0.001));

				int xx1 = screenWidth/2+(int)(( ((x-64)+1)*4 )/((z*4+200+zz)*0.001));
				int yy1 = 100-(int)(( landscape[x+1][z]-200 )/((z*4+200+zz)*0.001));

				int xx2 = screenWidth/2+(int)(( (x-64)*4 )/(((z+1)*4+200+zz)*0.001));
				int yy2 = 100-(int)(( landscape[x][z+1]-200 )/(((z+1)*4+200+zz)*0.001));

				int xx3 = screenWidth/2+(int)(( (x-64+1)*4 )/(((z+1)*4+200+zz)*0.001));
				int yy3 = 100-(int)(( landscape[x+1][z+1]-200 )/(((z+1)*4+200+zz)*0.001));

				//Machine.Line(x,200+z,x+1,500+z+1,RGB(0,0,landscape[x][z]));
				


				if (landscape[x][z]==0){ // Water
					int diff=10+z+rand()%20;
					if (diff<0)
						diff=0;
					if (diff>255)
						diff=255;
					
					g.setColor(new Color(0,0,diff));
				    int xpoints[] = {xx, xx1, xx2};
				    int ypoints[] = {yy, yy1, yy2};
				    int npoints = 3;
				    g.fillPolygon(xpoints, ypoints, npoints);					
					
					g.setColor(new Color(0,0,diff));
				    int xpoints2[] = {xx3, xx1, xx2};
				    int ypoints2[] = {yy3, yy1, yy2};
				    int npoints2 = 3;
				    g.fillPolygon(xpoints2, ypoints2, npoints2);					
					

				}
				else{			         // Mountain

					int diff=78+(yy-yy1)*2;
						if (diff>255) 
							diff=255;
						if (diff<0)
							diff=0;
						
						g.setColor(new Color(0,diff,0));
					    int xpoints[] = {xx, xx1, xx2};
					    int ypoints[] = {yy, yy1, yy2};
					    int npoints = 3;
					    g.fillPolygon(xpoints, ypoints, npoints);					
						
						g.setColor(new Color(0,diff,0));
					    int xpoints2[] = {xx3, xx1, xx2};
					    int ypoints2[] = {yy3, yy1, yy2};
					    int npoints2 = 3;
					    g.fillPolygon(xpoints2, ypoints2, npoints2);		
				}

			}
		}

	}	
	
	/*****************************************************************
	 * paint
	 * 
	 * @brief: draws output on screen
	 *****************************************************************/
	public void paint(Graphics g) {
		drawLandscape(g,300);
	}
	public void generateMountain(){
		this.setSize(screenWidth, screenHeight);
		
		int deviation,shift;

		// initialize graphics

		deviation=400;
		for (int x=0;x<MAX;x++){
			for (int y=0;y<MAX;y++){
				landscape[x][y]=-1000;
			}
		}
		landscape[0][0]=0;
		landscape[0][MAX]=0;
		landscape[MAX][0]=0;
		landscape[MAX][MAX]=0;
		genLandscape(0,0,MAX,MAX,deviation);
		
		this.validate();
		this.setVisible(true);
		this.repaint();
	}

}
