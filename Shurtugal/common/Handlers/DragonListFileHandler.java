package Shurtugal.common.Handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import Shurtugal.common.entity.Dragon.EntityTameableDragon;

public class DragonListFileHandler
{
	// TODO all the stuff for this file
	File DragonListFile;
	
	public DragonListFileHandler(MinecraftServer server)
	{

			DragonListFile = server.worldServers[0].getSaveHandler().getMapFileFromName("DragonsList");
			if(!DragonListFile.exists())
			{
				try
				{
					DragonListFile.createNewFile();
				}
				catch (IOException e)
				{
					System.out.println("File Could not be created");
					e.printStackTrace();
				}
			}
	}
	
	
	/**
	 * Gets an Instance of the players dragon to be spawn at players loc.
	 * 
	 * @param world
	 * @param player
	 * @return
	 */
	public EntityTameableDragon createDragonInstance(World world, EntityPlayer player)
	{
		if(!this.doesPlayerHaveDragon(player))
		{
			return null;
		}
		
		NBTTagCompound nbttagcompound = this.readfile();
		EntityTameableDragon dragon = new EntityTameableDragon(world);
		NBTTagCompound nbtDragon = nbttagcompound.getCompoundTag(player.username);
		dragon.readDragonFromNBT(nbtDragon);
		
		return dragon;
	}

	/**
	 * Clears all Info for the Dragon. only to be used for dragon death
	 * 
	 * @param player
	 */
	public void clearDragonFileForUser(EntityPlayer player)
	{
		try
		{
		FileOutputStream fileoutputstream = new FileOutputStream(this.DragonListFile);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		NBTTagCompound nbtDragon = new NBTTagCompound();
		nbttagcompound.setCompoundTag(player.username, nbtDragon);
		CompressedStreamTools.writeCompressed(nbttagcompound, fileoutputstream);
		fileoutputstream.close();
		System.out.println(player.username + "'s dragon was Killed");
		}
		catch(IOException e)
		{
		}
	}

	/**
	 * Returns weather or not that player has a dragon. used in the dragon egg
	 * code.
	 * 
	 * @param player
	 * @return
	 */
	public boolean doesPlayerHaveDragon(EntityPlayer player)
	{
		NBTTagCompound nbttagcompound = this.readfile();
		if(nbttagcompound != null)
		{
			return nbttagcompound.hasKey(player.username);
		}
		return false;
	}

	/**
	 * Updates the dragon in the DragonFile 
	 * 
	 * @param player
	 * @param dragon
	 */
	public void UpdatePlayerDragonInFile(String username, EntityTameableDragon dragon)
	{	
		try
		{
		FileOutputStream fileoutputstream = new FileOutputStream(this.DragonListFile);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		NBTTagCompound nbtDragon = new NBTTagCompound();
		dragon.writeDragonToNBT(nbtDragon);
		nbttagcompound.setCompoundTag(username, nbtDragon);
		CompressedStreamTools.writeCompressed(nbttagcompound, fileoutputstream);
		fileoutputstream.close();
		}
		catch(IOException e)
		{
		}
	}
	
	
	private NBTTagCompound readfile()
	{
		NBTTagCompound nbttagcompound = null;
		try
		{
			FileInputStream fileinputstream = new FileInputStream(this.DragonListFile);
			nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
		}
		catch (IOException e)
		{
		}
		return nbttagcompound;
	}

}
