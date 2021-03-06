package avi.mod.skrim.blocks.plants;

import avi.mod.skrim.SkrimGlobalConfig;
import avi.mod.skrim.world.gen.WorldGenWeirwood;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WeirwoodSapling extends CustomPlant {

  private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);

  public WeirwoodSapling() {
    super("weirwood_sapling", 15);
  }

  protected PropertyInteger getAgeProperty() {
    return AGE;
  }

  @Override
  public void finishedGrowing(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
    WorldGenWeirwood generator = new WorldGenWeirwood();
    generator.generate(worldIn, rand, pos);
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return SkrimGlobalConfig.DEBUG.value;
  }

}
