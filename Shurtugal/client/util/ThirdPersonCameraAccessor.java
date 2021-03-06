/*
 ** 2012 July 3
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package Shurtugal.client.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.src.ModLoader;

/**
 * 
 * @author Iamshortman
 */
public class ThirdPersonCameraAccessor
{

	private static final Logger L = Logger.getLogger(ThirdPersonCameraAccessor.class.getName());
	private final EntityRenderer renderer;
	private final float defaultDist;
	private final int feildIndex = 16;

	public ThirdPersonCameraAccessor(EntityRenderer renderer)
	{
		this.renderer = renderer;
		defaultDist = getThirdPersonDistance();
	}

	public ThirdPersonCameraAccessor()
	{
		this(Minecraft.getMinecraft().entityRenderer);
	}

	public void resetThirdPersonDistance()
	{
		setThirdPersonDistance(defaultDist);
	}

	public void setThirdPersonDistance(float dist)
	{
		try
		{
			ModLoader.setPrivateValue(EntityRenderer.class, renderer, feildIndex, dist);
		}
		catch (Exception ex)
		{
			L.log(Level.WARNING, "Can't set third person distance!", ex);
		}
	}

	public float getThirdPersonDistance()
	{
		try
		{
			return (Float) ModLoader.getPrivateValue(EntityRenderer.class, renderer, feildIndex);
		}
		catch (Exception ex)
		{
			L.log(Level.WARNING, "Can't get third person distance!", ex);
			return 0;
		}
	}

	public float getDefaultThirdPersonDistance()
	{
		return defaultDist;
	}
}
