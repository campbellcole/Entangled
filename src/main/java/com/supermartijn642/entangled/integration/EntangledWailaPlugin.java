package com.supermartijn642.entangled.integration;

import com.supermartijn642.core.TextComponents;
import com.supermartijn642.entangled.EntangledBlock;
import com.supermartijn642.entangled.EntangledBlockEntity;
import mcp.mobius.waila.api.*;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;

import java.util.List;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
@WailaPlugin("entangled")
public class EntangledWailaPlugin implements IComponentProvider, IWailaPlugin {

    @Override
    public void register(IRegistrar registrar){
        registrar.registerComponentProvider(this, TooltipPosition.BODY, EntangledBlock.class);
    }

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config){
        TileEntity tile = accessor.getTileEntity();
        if(tile instanceof EntangledBlockEntity){
            if(((EntangledBlockEntity)tile).isBound()){
                BlockState boundBlockState = ((EntangledBlockEntity)tile).getBoundBlockState();
                ITextComponent boundBlock = (boundBlockState == null ? TextComponents.string("Block") : TextComponents.blockState(boundBlockState)).color(TextFormatting.GOLD).get();
                BlockPos boundPos = ((EntangledBlockEntity)tile).getBoundBlockPos();
                ITextComponent x = TextComponents.string(Integer.toString(boundPos.getX())).color(TextFormatting.GOLD).get();
                ITextComponent y = TextComponents.string(Integer.toString(boundPos.getY())).color(TextFormatting.GOLD).get();
                ITextComponent z = TextComponents.string(Integer.toString(boundPos.getZ())).color(TextFormatting.GOLD).get();
                if(((EntangledBlockEntity)tile).getBoundDimensionIdentifier() == accessor.getWorld().getDimension().getType().getId())
                    tooltip.add(TextComponents.translation("entangled.waila.bound_same_dimension", boundBlock, x, y, z).color(TextFormatting.YELLOW).get());
                else{
                    ITextComponent dimension = TextComponents.dimension(DimensionType.getById(((EntangledBlockEntity)tile).getBoundDimensionIdentifier())).color(TextFormatting.GOLD).get();
                    tooltip.add(TextComponents.translation("entangled.waila.bound_other_dimension", boundBlock, x, y, z, dimension).color(TextFormatting.YELLOW).get());
                }
            }else
                tooltip.add(TextComponents.translation("entangled.waila.unbound").color(TextFormatting.YELLOW).get());
        }
    }
}
