import java.awt.Color;

import javax.security.auth.x500.X500Principal;

//import edu.princeton.cs.algs4.RedBlackBST;
//import edu.princeton.cs.introcs.Picture;



public class SeamCarver {
	
	private Picture pic;
	private int width ;
	private int height;
	private Color[][] color;
	private double[][] energyMatrix ;

	   public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
	   {
		   this.pic = picture;
		   this.width = picture.width();
		   this.height = picture.height();
		   this.energyMatrix = new double[this.height][this.width];
	       this.color = new Color[this.height][this.width];

	        for (int x = 0; x < this.width; x++) 
	           for (int y = 0; y < this.height; y++) {
	            	this.color[y][x] = this.pic.get(x, y);
	           }

	        for (int x = 0; x < this.width; x++) {
	           for (int y = 0; y < this.height; y++) {
	            	if (x==0 || x==this.width-1 || y==0 || y==this.height-1) this.energyMatrix[y][x] = 195075;            		
	            	else this.energyMatrix[y][x] = energy(x, y);
	             }
	       }
	       System.out.println(this.color[1][1].toString());
	   }
	   public Picture picture()                          // current picture
	   {
		   //System.out.println("current picture width: "+this.width);
		   Picture currentPic = new Picture(this.width, this.height);
		   for (int i=0;i<this.width;i++)
			   for (int j=0;j<this.height;j++)
		   		   currentPic.set(i, j, this.color[j][i]);
		   return currentPic;
	   }

	   public     int width()                            // width of current picture
	   {
		   return this.width;
	   }
	   public     int height()                           // height of current picture
	   {
		   return this.height;
	   }
	   public  double energy(int x, int y)               // energy of pixel at column x and row y
	   {
		   //Color color = this.pic.get(x, y);
		   Color color1x ;
		   Color color2x ;
		   Color color1y ;
		   Color color2y ;
		   
		   if (x==0) {
			   //color1x = this.pic.get(this.width()-1, y);
			   //color2x = this.pic.get(x+1, y);
			   color1x = this.color[y][this.width-1]; 
			   color2x = this.color[y][x+1];
		   } else if (x==this.width()-1) {
			   //color1x = this.pic.get(x-1, y);
			   //color2x = this.pic.get(0, y);
			   color1x = this.color[y][x-1];
			   color2x = this.color[y][0];
		   } else {
			   //color1x = this.pic.get(x-1, y);
			   //color2x = this.pic.get(x+1, y);
			   color1x = this.color[y][x-1];
			   color2x = this.color[y][x+1];
		   }
		   
		   if (y==0) {
			   //color1y = this.pic.get(x, this.height()-1);
			   //color2y = this.pic.get(x, y+1);
			   color1y = this.color[this.height-1][x];
			   color2y = this.color[y+1][x];
		   } else if (y==this.height-1) {
			   //color1y = this.pic.get(x, y-1);
			   //color2y = this.pic.get(x, 0);
			   color1y = this.color[y-1][x];
			   color2y = this.color[0][x];
		   } else {
			   //color1y = this.pic.get(x, y-1);
			   //color2y = this.pic.get(x, y+1);
			   color1y = this.color[y-1][x];
			   color2y = this.color[y+1][x];
		   }
			   
		   int Rx = Math.abs(color1x.getRed()-color2x.getRed());
		   int Gx = Math.abs(color1x.getGreen()-color2x.getGreen());
		   int Bx = Math.abs(color1x.getBlue()-color2x.getBlue());
		   int deltaxsqaure = Rx*Rx+Gx*Gx+Bx*Bx; 

		   int Ry = Math.abs(color1y.getRed()-color2y.getRed());
		   int Gy = Math.abs(color1y.getGreen()-color2y.getGreen());
		   int By = Math.abs(color1y.getBlue()-color2y.getBlue());
		   int deltaysqure = Ry*Ry+Gy*Gy+By*By;
		   
	       if (x==0 || x==this.width-1 || y==0 || y==this.height-1) return 195075;            		
	       else return deltaxsqaure+deltaysqure; 
	   }
	   public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
	   {
		   double[][] energyMatrix_transpose = transpose(this.energyMatrix);
		   return relaxVertex(energyMatrix_transpose);
	   }
	   
	   public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
	   {
		   return relaxVertex(this.energyMatrix);
		   
	   }
	   public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
	   {
		   //int oriWidth = this.width;
		   //Picture oriPic = new Picture(this.pic);

		   if (this.height>1) {
		   
			   int index = 0;
			   for (int x=0;x<this.width;x++) {
				   for (int y=0;y<this.height;y++) {
					   if (y== seam[index]) continue;
					   //else if (y > seam[index]) this.pic.set(x, y-1, this.pic.get(x, y));
					   else if (y > seam[index]) {
						   color[y-1][x] = color[y][x];  //update color
					   }
				   }
				   index++ ;
			   }
			   this.height--;

		   } else {
			   throw new java.lang.IllegalArgumentException();
		   }
		   
	   }
	   private void updateEnegyMatrix()
	   {
		   this.energyMatrix = new double[this.height][this.width];
	        for (int x = 0; x < this.width; x++) {
	            for (int y = 0; y < this.height; y++) {
	            	if (x==0 || x==this.width-1 || y==0 || y==this.height-1) this.energyMatrix[y][x] = 195075;            		
	            	else this.energyMatrix[y][x] = energy(x, y);
	            }
	        }
	   }
	   public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
	   {
		   //Picture oriPicture = new Picture(this.pic);
		   if (this.width>1) {
		   
			   int index = 0;
			   for (int y=0;y<this.height;y++) {
				   for (int x=0;x<this.width;x++) {
					   if (x == seam[index]) continue; 
//					   else if (x > seam[index]) this.pic.set(x-1, y, this.pic.get(x, y));
					   else if (x > seam[index]) {
						   color[y][x-1] = color[y][x]; //update color
					   }
				   }
				   index++;
			   }
			   this.width--;
			   updateEnegyMatrix();
		   } else {
			   throw new java.lang.IllegalArgumentException();
		   }
		   
	   }
	   
	   private double[][] transpose() 
	   {
		   double[][] energyMatrix_tranpose = new double[this.width][this.height];
		   for (int i=0;i<this.height;i++)
			   for (int j=0;j<this.width;j++)
			   {
				   energyMatrix_tranpose[j][i] = energyMatrix[i][j];
			   }
		   return energyMatrix_tranpose;
	   }
	   
	   private double[][] transpose(double[][] matrix)
	   {
		   int row = matrix.length;
		   int column = matrix[0].length;
		   double[][] matrix_transpose = new double[column][row]; 
		   for (int i=0;i<row;i++)
			   for (int j=0;j<column;j++)
			   {
				   matrix_transpose[j][i] = matrix[i][j];
			   }
		  
		   return matrix_transpose;
	   }
	   

	   private int[] relaxVertex(double[][] matrix)
	   {
	   	   int matrix_height = matrix.length;
	   	   		//System.out.println(matrix_height);
	   	   int matrix_width = matrix[0].length;
	   	   		//System.out.println(matrix_width);
	   	   int[] seam = new int[matrix_height];
	   	   double[][] minDist = new double[matrix_height][matrix_width];
	   	   int[][] indexFrom = new int[matrix_height][matrix_width]; //store x value only
	   	   for (int i=0;i<matrix_height;i++)
	   		   for (int j=0;j<matrix_width;j++)
	   		   {
	   			  if (i==0) minDist[i][j] = matrix[i][j];
	   			  else minDist[i][j] = Double.MAX_VALUE;
	   		   }
	   	   int source_column1;
	   	   int source_column2;
	   	   int source_column3;
	   	   for (int row=1;row<matrix_height;row++) {
	   		   for (int column=0; column<matrix_width; column++) {
	   			   if (column==0)  {
	   				   source_column1=0;
	   				   source_column2=0;
	   				   source_column3=1;
	   			   } else if (column==matrix_width-1) {
	   				   source_column1=matrix_width-2;
	   				   source_column2=matrix_width-1;
	   				   source_column3=matrix_width-1;
	   			   } else {     // 3 sources
	   				   source_column1=column-1;
	   				   source_column2=column;
	   				   source_column3=column+1;
	   			   } 
	   			   
	   			   if (minDist[row][column] > minDist[row-1][source_column1] + matrix[row][column]) {
	   				  minDist[row][column] =  minDist[row-1][source_column1] + matrix[row][column];
	   				  indexFrom[row][column] = source_column1;
	   			   }
	   			   if (minDist[row][column] > minDist[row-1][source_column2] + matrix[row][column]) {
	   				  minDist[row][column] =  minDist[row-1][source_column2] + matrix[row][column];
	   				  indexFrom[row][column] = source_column2;
	   			   }
	   			   if (minDist[row][column] > minDist[row-1][source_column3] + matrix[row][column]) {
	   				  minDist[row][column] =  minDist[row-1][source_column3] + matrix[row][column];
	   				  indexFrom[row][column] = source_column3;
	   			   }
	   		   }
	   	   }
	   	   
	   	   double min = Double.MAX_VALUE;
	   	   int column_last = 0;
	   	   for (int k=0;k<matrix_width-1;k++)
	   		   if (minDist[matrix_height-1][k] < min) {
	   			   min = minDist[matrix_height-1][k];
	   			   column_last = k;
	   		   }
	   	   
	   	   int column_number = column_last;
	   	   for (int row=matrix_height-1;row>=0;row--) {
	   		   seam[row] = column_number;
	   		   column_number =indexFrom[row][column_number];
	   	   }
	   	  
	   	   //for (int j=0;j<matrix_width;j++) {
	   	   //	System.out.print(minDist[1][j]+" ");
	   	   //}
	   	   
	   	   //System.out.println(minDist[matrix_height-1][column_last]);
	   	   //for (int i=0;i<seam.length;i++) {
	   	   //	 System.out.print(seam[i]+" ");
	   	   //}
	   	   //System.out.println(" ");
	   	return seam;   

	   } 

	   public static void main(String[] args) {
	        Picture pic = new Picture("12x10.png");
	        SeamCarver sc = new SeamCarver(pic);
	        int width  = sc.width();
	        int height = sc.height();

	        // convert to grayscale
	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                Color color = pic.get(x, y);
	                //System.out.println(color.getRed()+" "+color.getGreen()+" "+color.getBlue());
	                //System.out.println(sc.energy(x, y));
	            }
	        }
	       
	        //sc.relaxVertex(sc.energyMatrix);
	        //sc.findVerticalSeam();
	        //sc.findHorizontalSeam();
	        sc.removeVerticalSeam(sc.findVerticalSeam());
	        System.out.println("removed a vertical seam !");
            Picture outputImg = sc.picture();
	        System.out.println(sc.width());

	        
	        for (int i=0;i<sc.height();i++) {
	        	System.out.println(" ");
	        	for (int j=0;j<sc.width();j++) {
	        		System.out.print(sc.energyMatrix[i][j]+"     ");
	        	}
	        }
	        /*System.out.println(" ");
	        double[][] transpose = sc.transpose(sc.energyMatrix);
	        for (int i=0;i<width;i++) {
	        	System.out.println(" ");
	        	for (int j=0;j<height;j++) {
	        		System.out.print(transpose[i][j]+"     ");
	        	}
	        } */
	        
	        
	        
	        
	}
	   
}	   
