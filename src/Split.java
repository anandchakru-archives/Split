import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Split {
	private static boolean DEBUG = true;

	static void printusage() {
		printf("Split for %s %s\n", "Java", "1.0");
		printf("(c)1997-2000 Henk Hagedoorn\n");
		printf("(c)1997-2000 %s version Rhesa Rozendaal\n\n", "Java");
		printf("usage: %s\n", "java Split [ -c | -j | -s[size] | -? ] filename [outputpath]");
		printf("\ncommands:\n-j\t\tjoin files filename.001, filename.002... into filename\n");
		printf("-s[size]\tsplit filename in filename.001, filename.002...\n");
		printf(" \t\twhere each splitfile is size kB large (default is %s).\n", "1440kB");
		printf("-c\t\tcalculate checksum for file.\n");
		printf("-?\t\thelp (this text)\n");
		printf("[outputpath]\twhere Split should write it's output. If you do not\n");
		printf("\t\tspecify this, output goes to the current directory.\n");
		printf("\nFor more information, updates, versions for other OSs etc.\n");
		printf("visit us at \n\t\thttp://www.freebyte.com\n");
		printf("or contact us by email:\n");
		printf("\t\tHenk Hagedoorn <hjh@usa.net>\n");
		printf("\t\tRhesa Rozendaal <rhesa@usa.net>\n");
	}
	static int join(String s, String s1) {
		int i = 0x10000;
		int j = 1;
		int k = 0;
		int l = 0;
		int i1 = 0;
		byte abyte0[] = new byte[i];
		FileOutputStream fileoutputstream = null;
		File file = null;
		long l2 = System.currentTimeMillis();
		try {
			file = new File(s1);
			fileoutputstream = new FileOutputStream(file);
		} catch (Exception exception) {
			perror("Error opening output: " + exception.toString());
			return 10;
		}
		try {
			do {
				String s3 = s + createSuffix(j++);
				FileInputStream fileinputstream = new FileInputStream(s3);
				while ((k = fileinputstream.read(abyte0)) > 0) {
					l += k;
					try {
						fileoutputstream.write(abyte0, 0, k);
						i1 += k;
					} catch (Exception exception3) {
						perror("Error writing to output: " + exception3.toString());
						fileinputstream.close();
						return 10;
					}
				}
				fileinputstream.close();
			} while (true);
		} catch (Exception exception1) {
			if (DEBUG)
				perror("No more files to join: " + exception1.toString());
		}
		j--;
		try {
			fileoutputstream.close();
		} catch (Exception exception2) {
			if (DEBUG)
				perror("Error closing output: " + exception2.toString());
		}
		if (k == 0 && j == 1) {
			file.delete();
			perror("Error opening input file: Nothing to join.\n");
			return 5;
		} else {
			long l3 = System.currentTimeMillis() - l2;
			printf("Done!\n\n");
			printf("bytes read: %s\nbytes written: %s\n", String.valueOf(l), String.valueOf(i1));
			printf("time used: %s.%s sec.\n", String.valueOf(l3 / 1000L), String.valueOf(l3 % 1000L));
			return 0;
		}
	}
	static int checksum(String s) {
		FileInputStream fileinputstream = null;
		int i = 0;
		int j = 32768;
		int k = 0;
		int l = 0;
		byte abyte0[] = new byte[j];
		long l1 = System.currentTimeMillis();
		long l2 = 0L;
		try {
			fileinputstream = new FileInputStream(s);
		} catch (Exception exception) {
			perror("Error opening input file: " + exception);
			return 10;
		}
		try {
			while ((i = fileinputstream.read(abyte0)) > 0) {
				k += i;
				l = updcrc(l, i, abyte0);
			}
		} catch (Exception exception1) {
			perror("Error reading inputfile: " + exception1);
		}
		try {
			fileinputstream.close();
		} catch (Exception exception2) {
			if (DEBUG)
				perror("Error closing inputfile: " + exception2);
		}
		l2 = System.currentTimeMillis() - l1;
		printf("Done!\n\n");
		printf("bytes read: %s\n", String.valueOf(k));
		printf("time used: %s.%s sec.\n", String.valueOf(l2 / 1000L), String.valueOf(l2 % 1000L));
		printf("The checksum for %s is %s.\n", s, Integer.toHexString(l));
		return 0;
	}
	static int updcrc(int i, int j, byte abyte0[]) {
		int k = i;
		int l = i;
		int i1 = j - 1;
		while (i1-- > 0) {
			int j1 = abyte0[i1] << 8;
			int k1 = 0;
			do {
				j1 ^= k;
				if (j1 != 0) {
					if ((k & 0x8000) <= 0) {
						l <<= 1;
						l ^= 0x3fd;
					} else {
						l <<= 1;
					}
				} else {
					l <<= 1;
				}
				k = l;
				j1 <<= 1;
			} while (++k1 < 8);
		}
		return k;
	}
	static void info() {
		printf("Version:\t%s\n", "1.0");
		printf("Compile Date:\t%s\n", "11-3-00");
		printf("Compiler:\t%s\n", "Microsoft (R) Visual J++ Compiler Version 6.00.8424");
		printf("Os:\t\t%s\n", "Java");
	}
	static String replace(String s, String s1, String s2) {
		return replace(s, s1, s2, true);
	}
	static String replace(String s, String s1, String s2, boolean flag) {
		StringBuffer stringbuffer = new StringBuffer(s.length());
		int i = 0;
		int j = s1.length();
		try {
			if (j > 0) {
				int k;
				for (boolean flag2 = true; (k = s.indexOf(s1, i)) != -1 && flag2;) {
					flag2 = flag;
					stringbuffer.append(s.substring(i, k));
					stringbuffer.append(s2);
					i = k + j;
				}
			}
			stringbuffer.append(s.substring(i));
		} catch (Exception exception) {
		}
		return stringbuffer.toString();
	}
	public static void main(String args[]) {
		int i = args.length;
		char c = '\u05A0';
		int j = 5;
		try {
			if (args[i - 1].equals("-d"))
				DEBUG = true;
			switch (i) {
			case 1: // '\001'
				switch (args[0].charAt(0)) {
				case 45: // '-'
				case 47: // '/'
					switch (args[0].toLowerCase().charAt(1)) {
					case 63: // '?'
					case 104: // 'h'
						printusage();
						j = 0;
						break;
					case 105: // 'i'
						info();
						j = 0;
						break;
					case 99: // 'c'
					case 106: // 'j'
					case 115: // 's'
						printf("no inputfile specified\n");
						break;
					default:
						printf("invalid command %s\n", args[0]);
						break;
					}
					break;
				case 46: // '.'
				default:
					printf("no command specified\n");
					break;
				}
				break;
			default:
				int k = args[1].lastIndexOf(File.separatorChar);
				if (k == -1)
					k = args[1].indexOf(':');
				k++;
				String s1;
				if (i > 2) {
					j = args[2].length();
					s1 = args[2];
					if (args[2].charAt(j - 1) != ':' && args[2].charAt(j - 1) != File.separatorChar)
						s1 = s1 + File.separatorChar;
					s1 = s1 + args[1].substring(k);
				} else {
					s1 = args[1].substring(k);
				}
				label0: switch (args[0].charAt(0)) {
				case 45: // '-'
				case 47: // '/'
					switch (args[0].toLowerCase().charAt(1)) {
					case 106: // 'j'
						j = join(args[1], s1);
						break label0;
					case 115: // 's'
						Integer ainteger[];
						if (args[0].length() <= 2) {
							ainteger = new Integer[1];
							ainteger[0] = new Integer(c);
						} else {
							ArrayList<Integer> arraylist = new ArrayList<>();
							for (StringTokenizer stringtokenizer = new StringTokenizer(args[0].substring(2),
									"/ \\,"); stringtokenizer.hasMoreTokens(); arraylist
											.add(new Integer(atoi(stringtokenizer.nextToken()))));
							ainteger = (Integer[]) arraylist.toArray(new Integer[0]);
						}
						j = split(args[1], s1, ainteger);
						break;
					case 99: // 'c'
						j = checksum(args[1]);
						break;
					case 63: // '?'
					case 104: // 'h'
						printusage();
						System.exit(5);
						// fall through
					default:
						printf("invalid command %s\n", args[0]);
						j = 5;
						break;
					}
					break;
				case 46: // '.'
				default:
					printf("no command specified\n");
					break;
				}
				break;
			case 0: // '\0'
				break;
			}
		} catch (Exception exception) {
			perror("Invalid paramaters on command line\n");
		}
		if (j > 0)
			printf("usage: %s\n", "java Split [ -c | -j | -s[size] | -? ] filename [outputpath]");
		System.exit(j);
	}
	static void perror(String s) {
		System.err.println(s);
	}
	static String createSuffix(int i) {
		String s;
		for (s = String.valueOf(i); s.length() < 3; s = "0" + s);
		return "." + s;
	}
	static int atoi(String s) {
		try {
			int i;
			for (i = 0; i < s.length() && !Character.isDigit(s.charAt(i)); i++);
			int j;
			for (j = i; j < s.length() && Character.isDigit(s.charAt(j)); j++);
			if (DEBUG)
				perror("i=" + i + ", j=" + j);
			s = s.substring(i, j);
			if (DEBUG)
				perror(s);
			return Integer.parseInt(s);
		} catch (Exception exception) {
			if (DEBUG)
				perror("ex: " + exception);
		}
		return 1440;
	}
	static void printf(String s) {
		System.out.print(s);
	}
	static void printf(String s, String s1) {
		printf(replace(s, "%s", s1, false));
	}
	static void printf(String s, String s1, String s2) {
		String s3 = replace(s, "%s", s1, false);
		s3 = replace(s3, "%s", s2, false);
		printf(s3);
	}
	static int split(String s, String s1, Integer ainteger[]) {
		int i = 0;
		char c = '\u0400';
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		byte abyte0[] = new byte[c];
		FileInputStream fileinputstream = null;
		FileOutputStream fileoutputstream = null;
		long l1 = 0L;
		long l2 = System.currentTimeMillis();
		try {
			fileinputstream = new FileInputStream(s);
		} catch (Exception exception) {
			perror("Error opening input file: " + exception);
			try {
				fileinputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 10;
		}
		try {
			while ((l = fileinputstream.read(abyte0)) > 0) {
				if (k++ == ainteger[i].intValue() || j == 0) {
					if (j > 0) {
						k = 1;
						try {
							fileoutputstream.close();
							if (i < ainteger.length - 1)
								i++;
						} catch (Exception exception1) {
							if (DEBUG)
								perror("ex2: " + exception1);
						}
					}
					String s3 = s1 + createSuffix(++j);
					try {
						File file = new File(s3);
						fileoutputstream = new FileOutputStream(file);
					} catch (Exception exception4) {
						perror("Error opening output file: " + exception4);
						fileinputstream.close();
						return 10;
					}
				}
				i1 += l;
				j1 += l;
				fileoutputstream.write(abyte0, 0, l);
			}
		} catch (Exception exception2) {
			if (DEBUG)
				perror("ex1: " + exception2);
		}
		try {
			fileinputstream.close();
			fileoutputstream.close();
		} catch (Exception exception3) {
			if (DEBUG)
				perror("ex4: " + exception3);
		}
		l1 = System.currentTimeMillis() - l2;
		printf("Done!\n\n");
		printf("bytes read: %s\nbytes written: %s\n", String.valueOf(i1), String.valueOf(j1));
		printf("parts created: %s\n", "" + j);
		printf("time used: %s.%s sec.\n", String.valueOf(l1 / 1000L), String.valueOf(l1 % 1000L));
		return 0;
	}
}