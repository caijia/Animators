package com.cs.cj.http.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

public class StreamUtil {

	/**
	 * 将流中的数据读取成byte数组
	 * 
	 * @param inStream
	 *            输入流
	 * @return byte的数组
	 * @throws Exception
	 */
	public static byte[] readStreamByte(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	/**
	 * 将输入流中的数据写入到输出流中
	 * 
	 * @param inStream
	 *            输入流
	 * @param outStream
	 *            输出流
	 * @param closeStream
	 *            是否要关闭流
	 */
	public static void writeStream(InputStream inStream, OutputStream outStream, boolean closeStream) {
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outStream.flush();
				if (closeStream) {
					outStream.close();
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 讲输入流中的数据转化成字符串
	 * 
	 * @param inStream
	 *            输入流
	 * @return 流中的数据以字符串的形式返回
	 */
	public String readStreamString(InputStream inStream) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			writeStream(inStream, outStream, true);
			if (outStream != null)
				return new String(outStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取流中的数据 遇到回车换行时不读取
	 * 
	 * @param inStream
	 *            输入流
	 * @return 流中回车换行前的所有数据
	 * @throws Exception
	 */
	public static String read(PushbackInputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		int c;
		loop: while (true) {
			switch (c = inStream.read()) {
			case -1:
			case '\n':
				break loop;
			case '\r':
				int c2 = inStream.read();
				if (c2 != '\n' && c2 != -1)
					inStream.unread(c2);
				break loop;
			default:
				outStream.write(c);
			}
		}
		if (c != -1 || c != '\r' || c != '\n') {
			return new String(outStream.toByteArray());
		}
		return null;
	}
}
