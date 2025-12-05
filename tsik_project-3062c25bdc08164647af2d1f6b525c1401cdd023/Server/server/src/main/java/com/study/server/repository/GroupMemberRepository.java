package com.study.server.repository;

import com.study.server.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
  boolean existsByGroupAndUser(com.study.server.model.Group group, com.study.server.model.User user);
  java.util.List<GroupMember> findByGroup(com.study.server.model.Group group);
  java.util.List<GroupMember> findByUser(com.study.server.model.User user);
  Optional<GroupMember> findByGroupIdAndUserId(Long groupId, Long userId);
}
