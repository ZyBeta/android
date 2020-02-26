/**
 * ownCloud Android client application
 *
 * @author Abel García de Prada
 * Copyright (C) 2020 ownCloud GmbH.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.owncloud.android.data.authentication.repository

import com.owncloud.android.data.authentication.datasources.LocalAuthenticationDataSource
import com.owncloud.android.data.authentication.datasources.RemoteAuthenticationDataSource
import com.owncloud.android.domain.authentication.AuthenticationRepository
import com.owncloud.android.domain.server.model.ServerInfo
import com.owncloud.android.domain.user.model.UserInfo

class OCAuthenticationRepository(
    private val localAuthenticationDataSource: LocalAuthenticationDataSource,
    private val remoteAuthenticationDataSource: RemoteAuthenticationDataSource
) : AuthenticationRepository {
    override fun login(serverInfo: ServerInfo, username: String, password: String) {
        val userInfoAndRedirectionPath: Pair<UserInfo, String?> =
            remoteAuthenticationDataSource.login(
                serverPath = serverInfo.baseUrl,
                username = username,
                password = password
            )

        localAuthenticationDataSource.addAccountIfDoesNotExist(
            userInfoAndRedirectionPath.second,
            username,
            password,
            serverInfo,
            userInfoAndRedirectionPath.first
        )
    }
}