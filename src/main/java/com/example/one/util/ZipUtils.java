package com.example.one.util;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.zip.*;


public class ZipUtils {

	/**
	 * 递归压缩文件夹
	 *
	 * @param srcRootDir 压缩文件夹根目录的子路径
	 * @param file       当前递归压缩的文件或目录对象
	 * @param zos        压缩文件存储对象
	 * @throws Exception
	 */
	private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
		if (file == null) {
			return;
		}

		//如果是文件，则直接压缩该文件
		if (file.isFile()) {
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];

			//获取文件相对于压缩文件夹根目录的子路径
			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length() + File.separator.length());
			}
			subPath = subPath.substring(7);
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		}
		//如果是目录，则压缩整个目录
		else {
			//压缩目录中的文件或子目录
			File[] childFileList = file.listFiles();
			for (int n = 0; n < childFileList.length; n++) {
				childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}

	/**
	 * 对文件或文件目录进行压缩
	 *
	 * @param srcPath     要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
	 * @param zipPath     压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
	 * @param zipFileName 压缩文件名
	 * @throws Exception
	 */
	public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
		if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName)) {
			throw new FileNotFoundException(srcPath);
		}
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			File srcFile = new File(srcPath);

			// 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
			if (srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
				throw new Exception("zipPath must not be the child directory of srcPath.");
			}

			//判断压缩文件保存的路径是否存在，如果不存在，则创建目录
			File zipDir = new File(zipPath);
			if (!zipDir.exists() || !zipDir.isDirectory()) {
				zipDir.mkdirs();
			}

			//创建压缩文件保存的文件对象
			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				//检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
//				SecurityManager securityManager = new SecurityManager();
//				securityManager.checkDelete(zipFilePath);
				//删除已存在的目标文件
				zipFile.delete();
			}

			cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(cos);

			//如果只是压缩一个文件，则需要截取该文件的父目录
			String srcRootDir = srcPath;
			if (srcFile.isFile()) {
				int index = srcPath.lastIndexOf(File.separator);
				if (index != -1) {
					srcRootDir = srcPath.substring(0, index);
				}
			}
			//调用递归压缩方法进行目录或文件压缩
			zip(srcRootDir, srcFile, zos);
			zos.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 替换某个 item,
	 *
	 * @param zipInputStream  zip文件的zip输入流
	 * @param zipOutputStream 输出的zip输出流
	 * @param itemName        要替换的 item 名称
	 * @param itemInputStream 要替换的 item 的内容输入流
	 */
	public static void replaceItem(ZipInputStream zipInputStream,
								   ZipOutputStream zipOutputStream,
								   String itemName,
								   InputStream itemInputStream) {
		//
		if (null == zipInputStream) {
			return;
		}
		if (null == zipOutputStream) {
			return;
		}
		if (null == itemName) {
			return;
		}
		if (null == itemInputStream) {
			return;
		}
		//
		ZipEntry entryIn;
		try {
			while ((entryIn = zipInputStream.getNextEntry()) != null) {
				String entryName = entryIn.getName();
				ZipEntry entryOut = new ZipEntry(entryName);
				// 只使用 name
				zipOutputStream.putNextEntry(entryOut);
				// 缓冲区
				byte[] buf = new byte[8 * 1024];
				int len;

				if (entryName.equals(itemName)) {
					// 使用替换流
					while ((len = (itemInputStream.read(buf))) > 0) {
						zipOutputStream.write(buf, 0, len);
					}
				} else {
					// 输出普通Zip流
					while ((len = (zipInputStream.read(buf))) > 0) {
						zipOutputStream.write(buf, 0, len);
					}
				}
				// 关闭此 entry
				zipOutputStream.closeEntry();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//e.printStackTrace();
			close(itemInputStream);
			close(zipInputStream);
			close(zipOutputStream);
		}
	}

	/**
	 * 包装输入流
	 */
	public static ZipInputStream wrapZipInputStream(InputStream inputStream) {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		return zipInputStream;
	}

	/**
	 * 包装输出流
	 */
	public static ZipOutputStream wrapZipOutputStream(OutputStream outputStream) {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		return zipOutputStream;
	}

	private static void close(InputStream inputStream) {
		if (null != inputStream) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void close(OutputStream outputStream) {
		if (null != outputStream) {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {

		String scr="C:\\Users\\dell\\Desktop\\zipsource";
		String zip="C:\\Users\\dell\\Desktop\\down";
		try {
//			zip(scr,zip,"wly.zip");

//          File file=new File("C:\\Users\\dell\\Desktop\\down");
//
//			deleteFile(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void clearFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		deleteFile(file);
	}

	private static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
		}
		file.delete();
	}


}