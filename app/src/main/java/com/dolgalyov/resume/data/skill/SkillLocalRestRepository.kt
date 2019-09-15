package com.dolgalyov.resume.data.skill

import com.dolgalyov.resume.data.skill.source.SkillLocalSource
import com.dolgalyov.resume.data.skill.source.SkillRemoteSource

class SkillLocalRestRepository(
    localSource: SkillLocalSource,
    remoteSource: SkillRemoteSource
) : SkillRepository(localSource, remoteSource)