package ninja.bytecode.iris;

import java.io.File;

import org.bukkit.World.Environment;
import org.bukkit.block.Biome;

import com.google.gson.Gson;

import lombok.Data;
import ninja.bytecode.iris.object.IrisBiome;
import ninja.bytecode.iris.object.IrisBiomeDecorator;
import ninja.bytecode.iris.object.IrisDimension;
import ninja.bytecode.iris.object.IrisGenerator;
import ninja.bytecode.iris.object.IrisNoiseGenerator;
import ninja.bytecode.iris.object.IrisObjectPlacement;
import ninja.bytecode.iris.object.IrisRegion;
import ninja.bytecode.iris.util.IO;
import ninja.bytecode.iris.util.ObjectResourceLoader;
import ninja.bytecode.iris.util.ResourceLoader;
import ninja.bytecode.shuriken.json.JSONObject;

@Data
public class IrisDataManager
{
	private File dataFolder;
	private File packs;
	private ResourceLoader<IrisBiome> biomeLoader;
	private ResourceLoader<IrisRegion> regionLoader;
	private ResourceLoader<IrisDimension> dimensionLoader;
	private ResourceLoader<IrisGenerator> generatorLoader;
	private ObjectResourceLoader objectLoader;

	public void hotloaded()
	{
		packs.mkdirs();
		this.regionLoader = new ResourceLoader<>(packs, "regions", "Region", IrisRegion.class);
		this.biomeLoader = new ResourceLoader<>(packs, "biomes", "Biome", IrisBiome.class);
		this.dimensionLoader = new ResourceLoader<>(packs, "dimensions", "Dimension", IrisDimension.class);
		this.generatorLoader = new ResourceLoader<>(packs, "generators", "Generator", IrisGenerator.class);
		this.objectLoader = new ObjectResourceLoader(packs, "objects", "Object");
		writeExamples();
	}

	public IrisDataManager(File dataFolder)
	{
		this.dataFolder = dataFolder;
		this.packs = new File(dataFolder, "packs");
		hotloaded();
	}

	private void writeExamples()
	{
		File examples = new File(dataFolder, "example");
		examples.mkdirs();
		String biomes = "";
		String envs = "";

		for(Biome i : Biome.values())
		{
			biomes += i.name() + "\n";
		}

		for(Environment i : Environment.values())
		{
			envs += i.name() + "\n";
		}

		try
		{
			new File(examples, "example-pack/regions").mkdirs();
			new File(examples, "example-pack/biomes").mkdirs();
			new File(examples, "example-pack/dimensions").mkdirs();
			new File(examples, "example-pack/generators").mkdirs();
			IO.writeAll(new File(examples, "biome-list.txt"), biomes);
			IO.writeAll(new File(examples, "environment-list.txt"), envs);

			IrisGenerator gen = new IrisGenerator();
			IrisNoiseGenerator n = new IrisNoiseGenerator();
			n.setSeed(1000);
			IrisNoiseGenerator nf = new IrisNoiseGenerator();
			nf.setIrisBased(false);
			nf.setOctaves(3);
			nf.setOpacity(16);
			nf.setZoom(24);
			nf.setSeed(44);
			n.getFracture().add(nf);
			IrisNoiseGenerator nf2 = new IrisNoiseGenerator();
			nf2.setIrisBased(false);
			nf2.setOctaves(8);
			nf2.setOpacity(24);
			nf2.setZoom(64);
			nf2.setSeed(55);
			n.getFracture().add(nf2);
			gen.getComposite().add(n);

			IrisDimension dim = new IrisDimension();

			IrisRegion region = new IrisRegion();
			region.getLandBiomes().add("plains");
			region.getLandBiomes().add("desert");
			region.getLandBiomes().add("forest");
			region.getLandBiomes().add("mountains");
			region.getSeaBiomes().add("ocean");
			region.getShoreBiomes().add("beach");

			IrisObjectPlacement o = new IrisObjectPlacement();
			o.getPlace().add("schematic1");
			o.getPlace().add("schematic2");

			IrisBiome biome = new IrisBiome();
			biome.getChildren().add("another_biome");
			biome.getDecorators().add(new IrisBiomeDecorator());
			biome.getObjects().add(o);

			IO.writeAll(new File(examples, "example-pack/biomes/example-biome.json"), new JSONObject(new Gson().toJson(biome)).toString(4));
			IO.writeAll(new File(examples, "example-pack/regions/example-region.json"), new JSONObject(new Gson().toJson(region)).toString(4));
			IO.writeAll(new File(examples, "example-pack/dimensions/example-dimension.json"), new JSONObject(new Gson().toJson(dim)).toString(4));
			IO.writeAll(new File(examples, "example-pack/generators/example-generator.json"), new JSONObject(new Gson().toJson(gen)).toString(4));
		}

		catch(Throwable e)
		{

		}
	}
}