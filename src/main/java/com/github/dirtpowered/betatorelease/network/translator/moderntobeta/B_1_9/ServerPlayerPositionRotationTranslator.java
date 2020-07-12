/*
 * Copyright (c) 2020 Dirt Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.dirtpowered.betatorelease.network.translator.moderntobeta.B_1_9;

import com.github.dirtpowered.betaprotocollib.packet.Version_B1_7.data.PlayerLookMovePacketData;
import com.github.dirtpowered.betatorelease.BetaToRelease;
import com.github.dirtpowered.betatorelease.data.Constants;
import com.github.dirtpowered.betatorelease.data.player.BetaPlayer;
import com.github.dirtpowered.betatorelease.network.client.ModernClient;
import com.github.dirtpowered.betatorelease.network.session.ServerSession;
import com.github.dirtpowered.betatorelease.network.translator.model.ModernToBeta;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;

public class ServerPlayerPositionRotationTranslator implements ModernToBeta<ServerPlayerPositionRotationPacket> {

    @Override
    public void translate(BetaToRelease main, ServerPlayerPositionRotationPacket packet, ServerSession session, ModernClient modernClient) {
        BetaPlayer player = session.getBetaPlayer();

        double x = packet.getX();
        double y = packet.getY() + Constants.PLAYER_STANCE;
        double z = packet.getZ();

        float yaw = packet.getYaw();
        float pitch = packet.getPitch();

        boolean onGround = player.isOnGround();

        int teleportId = packet.getTeleportId();

        modernClient.sendPacket(new ClientPlayerPositionRotationPacket(onGround, x, y, z, yaw, pitch));
        modernClient.sendPacket(new ClientTeleportConfirmPacket(teleportId));

        session.sendPacket(new PlayerLookMovePacketData(x, y, y, z, yaw, pitch, onGround));
    }
}