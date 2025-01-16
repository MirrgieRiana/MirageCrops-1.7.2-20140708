package plugincrops.coremod.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HFile
{

	public static URL getResource(String absolutePath)
	{
		String path = absolutePath.substring(1);

		URL url;

		url = ClassLoader.getSystemClassLoader().getResource(path);

		if (url == null) {
			url = HDeobf.class.getClassLoader().getResource(path);
		}

		return url;
	}

	public static byte[] getBytesFromClassName(String className) throws HelperException
	{
		return getBytes("/" + className.replaceAll("\\.", "/") + ".class");
	}

	public static byte[] getBytes(String absolutePath) throws HelperException
	{
		URL url = HFile.getResource(absolutePath);

		if (url == null) {
			throw new HelperException(absolutePath);
		}

		return getBytes(url);
	}

	public static byte[] getBytes(URL url) throws HelperException
	{
		InputStream is;
		try {
			is = url.openStream();
		} catch (IOException e) {
			throw new HelperException(url.toString(), e);
		}

		byte[] bytes;
		try {
			bytes = getBytes(is);
		} catch (HelperException e) {
			throw new HelperException(url.toString(), e);
		}

		return bytes;
	}

	public static byte[] getBytes(InputStream is) throws HelperException
	{
		ArrayList<byte[]> bytesList = new ArrayList<byte[]>();
		ArrayList<Integer> lengthList = new ArrayList<Integer>();

		while (true) {
			byte[] buf = new byte[2048];

			int res;
			try {
				res = is.read(buf);
			} catch (IOException e) {
				try {
					is.close();
				} catch (IOException e1) {
					throw new HelperException(e1);
				}
				throw new HelperException(e);
			}

			if (res == -1) {
				break;
			}

			bytesList.add(buf);
			lengthList.add(res);
		}

		int size = 0;
		for (int i = 0; i < lengthList.size(); i++) {
			size += lengthList.get(i);
		}

		byte[] buf = new byte[size];

		int index = 0;
		for (int i = 0; i < bytesList.size(); i++) {
			System.arraycopy(bytesList.get(i), 0, buf, index, lengthList.get(i));
			index += lengthList.get(i);
		}

		return buf;
	}

}
