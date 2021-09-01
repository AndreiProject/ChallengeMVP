package com.paramonov.challenge.data.repository.local

import com.paramonov.challenge.data.repository.local.platform.PlatformRepository
import com.paramonov.challenge.data.repository.local.room.RoomRepository

interface LocalRepository : RoomRepository, PlatformRepository