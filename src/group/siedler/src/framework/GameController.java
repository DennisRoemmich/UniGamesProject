package framework;

import java.util.ArrayList;
import java.util.List;

import controller.GameState;
import materials.MaterialSet;
import player.PlayerColor;
import player.PlayerData;

public class GameController {

	protected List<Player> mPlayers = new ArrayList<>();
    protected List<PlayerData> mPlayerData = new ArrayList<>();
    protected GameState mState = GameState.NOT_RUNNING;
	private Presenter mPresenter;

	public void callPresenterUpdate() {
		if (mPresenter != null) {
			mPresenter.refreshOutput();
		}
	}

	public Presenter getPresenter() {
		return mPresenter;
	}

	public void setPresenter(Presenter mPresenter) {
		this.mPresenter = mPresenter;
	}
	
    public int getNumberOfPlayers() {

        return mPlayers.size();
    }
    
    public List<PlayerData> getPlayerData() {

    	return mPlayerData;
    }

    public void addPlayer(Player player, PlayerColor color) {

        if (mState != GameState.NOT_RUNNING) {
            return;
        }
        if (mPlayerData.stream().anyMatch(playerData -> playerData.getColor() == color)) {
            return;
        }
        mPlayers.add(player);
        PlayerData data = new PlayerData(color);
        data.setHand(new MaterialSet());
        mPlayerData.add(data);
    }
}
