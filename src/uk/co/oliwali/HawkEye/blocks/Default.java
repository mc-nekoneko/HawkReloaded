package uk.co.oliwali.HawkEye.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.database.DataManager;
import uk.co.oliwali.HawkEye.entry.BlockEntry;
import uk.co.oliwali.HawkEye.util.BlockUtil;

public class Default implements HawkBlock {

	@Override
	public void Restore(Block b, int id, int data) {
		b.setTypeIdAndData(id, ((byte) data), true);
	}

	@Override
	public void logAttachedBlocks(Block b, Player p, DataType type) {
		Block topb = b.getRelative(BlockFace.UP);
		HawkBlock hb = HawkBlockType.getHawkBlock(topb.getTypeId());
		if (hb.isTopBlock()) {
			hb.logAttachedBlocks(topb, p, type);
			DataManager.addEntry(new BlockEntry(p, type, hb.getCorrectBlock(topb)));
		}

		for(BlockFace face: BlockUtil.faces) {
			Block attch = b.getRelative(face);
			hb = HawkBlockType.getHawkBlock(attch.getTypeId());
			if (hb.isAttached()) {
				DataManager.addEntry(new BlockEntry(p, type, hb.getCorrectBlock(attch)));
			}
		}
	}

	@Override
	public Block getCorrectBlock(Block b) {
		return b;
	}

	@Override
	public boolean isTopBlock() {
		return false;
	}
	
	@Override
	public boolean isAttached() {
		return false;
	}
}
