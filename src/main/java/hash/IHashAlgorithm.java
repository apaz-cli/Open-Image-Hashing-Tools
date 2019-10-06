package hash;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import image.IImage;
import utils.ImageUtils;

public interface IHashAlgorithm {

	// TODO figure out what I'm doing with matching
	// Idea: Have the algorithms do their own matching. Get rid of the similarity
	// distance thing.
	// cont: Create an abstract method in this interface that takes two ImageHashes,
	// and returns a boolean if they match or not. Maybe have some way to adjust how
	// sloppy the match is?

	
	// Used for writing results of hash to file.
	abstract String getHashName();


	abstract boolean matches(ImageHash hash1, ImageHash hash2, MatchMode mode);
	default boolean matches(ImageHash hash1, ImageHash hash2) {
		return this.matches(hash1, hash2, MatchMode.NORMAL);
	} 
	

	// Using IImage guarantees that nothing must be changed, even if more IImage
	// implementations are added.
	abstract ImageHash hash(IImage<?> img);

	// For speed, let the specific algorithm perform its own narrowing conversion to
	// its preferred type, rather than providing default implementation. For
	// example, RGBHistogramHash uses RGBImage as its preferred type, whereas
	// AverageHash uses GreyscaleImage.
	abstract ImageHash hash(BufferedImage img);

	default ImageHash hash(File imgFile) throws IOException {
		return this.hash(ImageIO.read(imgFile));
	}

	default ImageHash hash(URL imgURL) throws IOException {
		return this.hash(ImageUtils.openImage(imgURL));
	}
}
