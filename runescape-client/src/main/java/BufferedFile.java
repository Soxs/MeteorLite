import java.io.EOFException;
import java.io.IOException;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("nf")
@Implements("BufferedFile")
public class BufferedFile {
	@ObfuscatedName("n")
	@ObfuscatedSignature(
		descriptor = "Lnm;"
	)
	@Export("accessFile")
	AccessFile accessFile;
	@ObfuscatedName("f")
	@Export("readBuffer")
	byte[] readBuffer;
	@ObfuscatedName("y")
	@ObfuscatedGetter(
		longValue = 4024474701341933375L
	)
	@Export("readBufferOffset")
	long readBufferOffset;
	@ObfuscatedName("p")
	@ObfuscatedGetter(
		intValue = -1725800063
	)
	@Export("readBufferLength")
	int readBufferLength;
	@ObfuscatedName("j")
	@Export("writeBuffer")
	byte[] writeBuffer;
	@ObfuscatedName("r")
	@ObfuscatedGetter(
		longValue = 8737860925507049089L
	)
	@Export("writeBufferOffset")
	long writeBufferOffset;
	@ObfuscatedName("b")
	@ObfuscatedGetter(
		intValue = -97062741
	)
	@Export("writeBufferLength")
	int writeBufferLength;
	@ObfuscatedName("d")
	@ObfuscatedGetter(
		longValue = -3326324260793149209L
	)
	@Export("offset")
	long offset;
	@ObfuscatedName("s")
	@ObfuscatedGetter(
		longValue = -6829996619110357721L
	)
	@Export("fileLength")
	long fileLength;
	@ObfuscatedName("u")
	@ObfuscatedGetter(
		longValue = 3454228653685997387L
	)
	@Export("length")
	long length;
	@ObfuscatedName("l")
	@ObfuscatedGetter(
		longValue = 1780782613106431365L
	)
	@Export("fileOffset")
	long fileOffset;

	@ObfuscatedSignature(
		descriptor = "(Lnm;II)V"
	)
	public BufferedFile(AccessFile var1, int var2, int var3) throws IOException {
		this.readBufferOffset = -1L; // L: 9
		this.writeBufferOffset = -1L; // L: 12
		this.writeBufferLength = 0; // L: 13
		this.accessFile = var1; // L: 20
		this.length = this.fileLength = var1.length(); // L: 21
		this.readBuffer = new byte[var2]; // L: 22
		this.writeBuffer = new byte[var3]; // L: 23
		this.offset = 0L; // L: 24
	} // L: 25

	@ObfuscatedName("v")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "116"
	)
	@Export("close")
	public void close() throws IOException {
		this.flush(); // L: 28
		this.accessFile.close(); // L: 29
	} // L: 30

	@ObfuscatedName("n")
	@Export("seek")
	public void seek(long var1) throws IOException {
		if (var1 < 0L) { // L: 33
			throw new IOException("");
		} else {
			this.offset = var1; // L: 34
		}
	} // L: 35

	@ObfuscatedName("f")
	@ObfuscatedSignature(
		descriptor = "(B)J",
		garbageValue = "17"
	)
	@Export("length")
	public long length() {
		return this.length; // L: 38
	}

	@ObfuscatedName("y")
	@ObfuscatedSignature(
		descriptor = "([BB)V",
		garbageValue = "-4"
	)
	@Export("readFully")
	public void readFully(byte[] var1) throws IOException {
		this.read(var1, 0, var1.length); // L: 42
	} // L: 43

	@ObfuscatedName("p")
	@ObfuscatedSignature(
		descriptor = "([BIII)V",
		garbageValue = "1635241805"
	)
	@Export("read")
	public void read(byte[] var1, int var2, int var3) throws IOException {
		try {
			if (var3 + var2 > var1.length) { // L: 47
				throw new ArrayIndexOutOfBoundsException(var3 + var2 - var1.length);
			}

			if (this.writeBufferOffset != -1L && this.offset >= this.writeBufferOffset && (long)var3 + this.offset <= (long)this.writeBufferLength + this.writeBufferOffset) { // L: 48
				System.arraycopy(this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var1, var2, var3); // L: 49
				this.offset += (long)var3; // L: 50
				return; // L: 51
			}

			long var4 = this.offset; // L: 53
			int var7 = var3; // L: 55
			int var8;
			if (this.offset >= this.readBufferOffset && this.offset < this.readBufferOffset + (long)this.readBufferLength) { // L: 56
				var8 = (int)((long)this.readBufferLength - (this.offset - this.readBufferOffset)); // L: 57
				if (var8 > var3) { // L: 58
					var8 = var3;
				}

				System.arraycopy(this.readBuffer, (int)(this.offset - this.readBufferOffset), var1, var2, var8); // L: 59
				this.offset += (long)var8; // L: 60
				var2 += var8; // L: 61
				var3 -= var8; // L: 62
			}

			if (var3 > this.readBuffer.length) { // L: 64
				this.accessFile.seek(this.offset); // L: 65

				for (this.fileOffset = this.offset; var3 > 0; var3 -= var8) { // L: 66 67 73
					var8 = this.accessFile.read(var1, var2, var3); // L: 68
					if (var8 == -1) { // L: 69
						break;
					}

					this.fileOffset += (long)var8; // L: 70
					this.offset += (long)var8; // L: 71
					var2 += var8; // L: 72
				}
			} else if (var3 > 0) { // L: 76
				this.load(); // L: 77
				var8 = var3; // L: 78
				if (var3 > this.readBufferLength) { // L: 79
					var8 = this.readBufferLength;
				}

				System.arraycopy(this.readBuffer, 0, var1, var2, var8); // L: 80
				var2 += var8; // L: 81
				var3 -= var8; // L: 82
				this.offset += (long)var8; // L: 83
			}

			if (-1L != this.writeBufferOffset) { // L: 85
				if (this.writeBufferOffset > this.offset && var3 > 0) { // L: 86
					var8 = var2 + (int)(this.writeBufferOffset - this.offset); // L: 87
					if (var8 > var3 + var2) { // L: 88
						var8 = var3 + var2;
					}

					while (var2 < var8) { // L: 89
						var1[var2++] = 0; // L: 90
						--var3; // L: 91
						++this.offset; // L: 92
					}
				}

				long var13 = -1L; // L: 95
				long var10 = -1L; // L: 96
				if (this.writeBufferOffset >= var4 && this.writeBufferOffset < (long)var7 + var4) { // L: 97
					var13 = this.writeBufferOffset; // L: 98
				} else if (var4 >= this.writeBufferOffset && var4 < this.writeBufferOffset + (long)this.writeBufferLength) { // L: 100
					var13 = var4; // L: 101
				}

				if ((long)this.writeBufferLength + this.writeBufferOffset > var4 && this.writeBufferOffset + (long)this.writeBufferLength <= (long)var7 + var4) { // L: 103
					var10 = this.writeBufferOffset + (long)this.writeBufferLength; // L: 104
				} else if (var4 + (long)var7 > this.writeBufferOffset && (long)var7 + var4 <= (long)this.writeBufferLength + this.writeBufferOffset) { // L: 106
					var10 = var4 + (long)var7; // L: 107
				}

				if (var13 > -1L && var10 > var13) { // L: 109
					int var12 = (int)(var10 - var13); // L: 110
					System.arraycopy(this.writeBuffer, (int)(var13 - this.writeBufferOffset), var1, (int)(var13 - var4) + var2, var12); // L: 111
					if (var10 > this.offset) { // L: 112
						var3 = (int)((long)var3 - (var10 - this.offset)); // L: 113
						this.offset = var10; // L: 114
					}
				}
			}
		} catch (IOException var16) { // L: 120
			this.fileOffset = -1L; // L: 121
			throw var16; // L: 122
		}

		if (var3 > 0) { // L: 124
			throw new EOFException();
		}
	} // L: 125

	@ObfuscatedName("j")
	@ObfuscatedSignature(
		descriptor = "(B)V",
		garbageValue = "104"
	)
	@Export("load")
	void load() throws IOException {
		this.readBufferLength = 0; // L: 128
		if (this.offset != this.fileOffset) { // L: 129
			this.accessFile.seek(this.offset); // L: 130
			this.fileOffset = this.offset; // L: 131
		}

		int var2;
		for (this.readBufferOffset = this.offset; this.readBufferLength < this.readBuffer.length; this.readBufferLength += var2) { // L: 133 134 140
			int var1 = this.readBuffer.length - this.readBufferLength; // L: 135
			if (var1 > 200000000) { // L: 136
				var1 = 200000000;
			}

			var2 = this.accessFile.read(this.readBuffer, this.readBufferLength, var1); // L: 137
			if (var2 == -1) { // L: 138
				break;
			}

			this.fileOffset += (long)var2; // L: 139
		}

	} // L: 142

	@ObfuscatedName("r")
	@ObfuscatedSignature(
		descriptor = "([BIII)V",
		garbageValue = "231162903"
	)
	@Export("write")
	public void write(byte[] var1, int var2, int var3) throws IOException {
		try {
			if ((long)var3 + this.offset > this.length) { // L: 146
				this.length = (long)var3 + this.offset;
			}

			if (this.writeBufferOffset != -1L && (this.offset < this.writeBufferOffset || this.offset > this.writeBufferOffset + (long)this.writeBufferLength)) { // L: 147
				this.flush(); // L: 148
			}

			if (this.writeBufferOffset != -1L && this.offset + (long)var3 > (long)this.writeBuffer.length + this.writeBufferOffset) { // L: 150
				int var4 = (int)((long)this.writeBuffer.length - (this.offset - this.writeBufferOffset)); // L: 151
				System.arraycopy(var1, var2, this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var4); // L: 152
				this.offset += (long)var4; // L: 153
				var2 += var4; // L: 154
				var3 -= var4; // L: 155
				this.writeBufferLength = this.writeBuffer.length; // L: 156
				this.flush(); // L: 157
			}

			if (var3 <= this.writeBuffer.length) { // L: 159
				if (var3 > 0) { // L: 188
					if (-1L == this.writeBufferOffset) { // L: 189
						this.writeBufferOffset = this.offset;
					}

					System.arraycopy(var1, var2, this.writeBuffer, (int)(this.offset - this.writeBufferOffset), var3); // L: 190
					this.offset += (long)var3; // L: 191
					if (this.offset - this.writeBufferOffset > (long)this.writeBufferLength) { // L: 192
						this.writeBufferLength = (int)(this.offset - this.writeBufferOffset);
					}

				}
			} else {
				if (this.fileOffset != this.offset) { // L: 160
					this.accessFile.seek(this.offset); // L: 161
					this.fileOffset = this.offset; // L: 162
				}

				this.accessFile.write(var1, var2, var3); // L: 164
				this.fileOffset += (long)var3; // L: 165
				if (this.fileOffset > this.fileLength) { // L: 166
					this.fileLength = this.fileOffset;
				}

				long var9 = -1L; // L: 167
				long var6 = -1L; // L: 168
				if (this.offset >= this.readBufferOffset && this.offset < this.readBufferOffset + (long)this.readBufferLength) { // L: 169
					var9 = this.offset; // L: 170
				} else if (this.readBufferOffset >= this.offset && this.readBufferOffset < this.offset + (long)var3) { // L: 172
					var9 = this.readBufferOffset; // L: 173
				}

				if (this.offset + (long)var3 > this.readBufferOffset && this.offset + (long)var3 <= (long)this.readBufferLength + this.readBufferOffset) { // L: 175
					var6 = this.offset + (long)var3; // L: 176
				} else if ((long)this.readBufferLength + this.readBufferOffset > this.offset && (long)this.readBufferLength + this.readBufferOffset <= (long)var3 + this.offset) { // L: 178
					var6 = this.readBufferOffset + (long)this.readBufferLength; // L: 179
				}

				if (var9 > -1L && var6 > var9) { // L: 181
					int var8 = (int)(var6 - var9); // L: 182
					System.arraycopy(var1, (int)(var9 + (long)var2 - this.offset), this.readBuffer, (int)(var9 - this.readBufferOffset), var8); // L: 183
				}

				this.offset += (long)var3; // L: 185
			}
		} catch (IOException var12) { // L: 196
			this.fileOffset = -1L; // L: 197
			throw var12; // L: 198
		}
	} // L: 186 193 200

	@ObfuscatedName("b")
	@ObfuscatedSignature(
		descriptor = "(I)V",
		garbageValue = "1231168990"
	)
	@Export("flush")
	void flush() throws IOException {
		if (this.writeBufferOffset != -1L) { // L: 203
			if (this.fileOffset != this.writeBufferOffset) { // L: 204
				this.accessFile.seek(this.writeBufferOffset); // L: 205
				this.fileOffset = this.writeBufferOffset; // L: 206
			}

			this.accessFile.write(this.writeBuffer, 0, this.writeBufferLength); // L: 208
			this.fileOffset += (long)this.writeBufferLength * -27464314403160063L; // L: 209
			if (this.fileOffset > this.fileLength) { // L: 210
				this.fileLength = this.fileOffset;
			}

			long var1 = -1L; // L: 211
			long var3 = -1L; // L: 212
			if (this.writeBufferOffset >= this.readBufferOffset && this.writeBufferOffset < this.readBufferOffset + (long)this.readBufferLength) { // L: 213
				var1 = this.writeBufferOffset; // L: 214
			} else if (this.readBufferOffset >= this.writeBufferOffset && this.readBufferOffset < (long)this.writeBufferLength + this.writeBufferOffset) { // L: 216
				var1 = this.readBufferOffset; // L: 217
			}

			if ((long)this.writeBufferLength + this.writeBufferOffset > this.readBufferOffset && (long)this.writeBufferLength + this.writeBufferOffset <= this.readBufferOffset + (long)this.readBufferLength) { // L: 219
				var3 = this.writeBufferOffset + (long)this.writeBufferLength; // L: 220
			} else if ((long)this.readBufferLength + this.readBufferOffset > this.writeBufferOffset && this.readBufferOffset + (long)this.readBufferLength <= this.writeBufferOffset + (long)this.writeBufferLength) { // L: 222
				var3 = (long)this.readBufferLength + this.readBufferOffset; // L: 223
			}

			if (var1 > -1L && var3 > var1) { // L: 225
				int var5 = (int)(var3 - var1); // L: 226
				System.arraycopy(this.writeBuffer, (int)(var1 - this.writeBufferOffset), this.readBuffer, (int)(var1 - this.readBufferOffset), var5); // L: 227
			}

			this.writeBufferOffset = -1L; // L: 229
			this.writeBufferLength = 0; // L: 230
		}

	} // L: 232

	@ObfuscatedName("ah")
	@ObfuscatedSignature(
		descriptor = "(Ljava/lang/String;I)I",
		garbageValue = "-387323412"
	)
	public static int method6418(String var0) {
		return var0.length() + 2; // L: 126
	}
}