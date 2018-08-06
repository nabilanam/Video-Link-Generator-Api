package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {YoutubeIdFinder.class})
public class YoutubeIdFinderTest {

	@Autowired
	private YoutubeIdFinder idFinder;

	//video url

	@Test
	public void whenVideoUrlHasRegularRegex_thenReturnsVideoId(){
		//given
		String regularUrlRegex = ".+youtube\\..+?/watch.*?v=(.*?)(?:&|/|$)";
		String url  = "https://www.youtube.com/watch?v=yIVRs6YSbOM";

		//when
		String id = idFinder.findVideoId(url, regularUrlRegex);

		//then
		assertThat(id).isEqualTo("yIVRs6YSbOM");
	}

	@Test
	public void whenVideoUrlHasShortUrlRegex_thenReturnsVideoId(){
		//given
		String shortUrlRegex = ".+youtu\\.be/(.*?)(?:\\?|&|/|$)";
		String url  = "https://youtu.be/yIVRs6YSbOM";

		//when
		String id = idFinder.findVideoId(url, shortUrlRegex);

		//then
		assertThat(id).isEqualTo("yIVRs6YSbOM");
	}

	@Test
	public void whenVideoUrlHasEmbedUrlRegex_thenReturnsVideoId(){
		//given
		String embedUrlRegex = ".+youtube\\..+?/embed/(.*?)(?:\\?|&|/|$)";
		String url  = "https://www.youtube.com/embed/yIVRs6YSbOM";

		//when
		String id = idFinder.findVideoId(url, embedUrlRegex);

		//then
		assertThat(id).isEqualTo("yIVRs6YSbOM");
	}


	//playlist url

	@Test
	public void whenPlaylistUrlHasRegularPlaylistUrlRegex_thenReturnsPlaylistId(){
		//given
		String regularPlaylistUrlRegex = ".+youtube\\..+?/playlist.*?list=(.*?)(?:&|/|$)";
		String url  = "https://www.youtube.com/playlist?list=PLOU2XLYxmsIJGErt5rrCqaSGTMyyqNt2H";

		//when
		String id = idFinder.findPlaylistId(url, regularPlaylistUrlRegex);

		//then
		assertThat(id).isEqualTo("PLOU2XLYxmsIJGErt5rrCqaSGTMyyqNt2H");
	}

	@Test
	public void whenPlaylistUrlHasCompositePlaylistUrlRegex_thenReturnsPlaylistId(){
		//given
		String compositePlaylistUrlRegex = ".+youtube\\..+?/watch.*?list=(.*?)(?:&|/|$)";
		String url  = "https://www.youtube.com/watch?v=b8m9zhNAgKs&list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr";

		//when
		String id = idFinder.findPlaylistId(url, compositePlaylistUrlRegex);

		//then
		assertThat(id).isEqualTo("PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr");
	}

	@Test
	public void whenPlaylistUrlHasShortCompositePlaylistUrlRegex_thenReturnsPlaylistId(){
		//given
		String shortCompositePlaylistUrlRegex = ".+youtu\\.be/.*?/.*?list=(.*?)(?:&|/|$)";
		String url  = "https://youtu.be/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr";

		//when
		String id = idFinder.findPlaylistId(url, shortCompositePlaylistUrlRegex);

		//then
		assertThat(id).isEqualTo("PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr");
	}

	@Test
	public void whenPlaylistUrlHasEmbedCompositePlaylistUrlRegex_thenReturnsPlaylistId(){
		//given
		String embedCompositePlaylistUrlRegex = ".+youtube\\..+?/embed/.*?/.*?list=(.*?)(?:&|/|$)";
		String url  = "https://www.youtube.com/embed/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr";

		//when
		String id = idFinder.findPlaylistId(url, embedCompositePlaylistUrlRegex);

		//then
		assertThat(id).isEqualTo("PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr");
	}
}
