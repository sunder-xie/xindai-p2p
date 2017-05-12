package com.qfw.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;

import com.qfw.common.util.web.ViewOper;

public class CreateImageCodeUtils implements Serializable {

    private static final long serialVersionUID = -8161565799237268271L;
   
    private static final Integer width = 60;
    private static final Integer height = 20;
    private static final Color bankgroud = new Color(0xFFFFFF);
    private static final Color drawColor = new Color(0x000000);
    private static final Font textFont = new Font("Comic Sans ms", Font.PLAIN, 18);
    private static final String str = "0,a,2,x,e,5,j,7,l,9,1,b,c,d,4,f,g,h,i,6,k,8,m,n,o,p,q,r,s,t,u,v,w,3,y,z";
    private static final Random random = new Random();

    public static DefaultStreamedContent createImage(){
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(bankgroud);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(drawColor);
        graphics.drawRect(0, 0, width - 1, height - 1);
        graphics.setFont(textFont);
        // 绘制干扰线
        for (int i = 0; i < 160; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.setColor(getRandColor(100, 250));
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        // 绘制验证码
        graphics.setColor(drawColor);
        String code = "";
        String[] array = str.split(",");
        for (int i = 0; i < 4; i++) {
            String rand = array[random.nextInt(36)];
            code += rand;
            graphics.drawString(rand, 13 * i + 6, 16);
        }
        ViewOper.getSession().setAttribute("code", code);
        graphics.dispose();    
        try {
        	FileOutputStream fout = new FileOutputStream(new File("D:/"+code+".png"));
			ImageIO.write(image, "png", out);
			ImageIO.write(image, "png", fout);
			out.close();
			fout.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 
		}        
        return new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), "image/png");
    }

    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
       

}

