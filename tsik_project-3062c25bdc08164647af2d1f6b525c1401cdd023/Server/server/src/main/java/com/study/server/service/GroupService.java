package com.study.server.service;

import com.study.server.dto.GroupDto;
import com.study.server.model.Group;
import com.study.server.model.GroupMember;
import com.study.server.model.User;
import com.study.server.repository.GroupMemberRepository;
import com.study.server.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class GroupService {
  private final GroupRepository groupRepository;
  private final GroupMemberRepository groupMemberRepository;
  private final UserService userService;

  public GroupDto create(GroupDto dto) {
    User creator = userService.getEntity(dto.getCreatorId());
    Group g = Group.builder().name(dto.getName()).creator(creator).build();
    g = groupRepository.save(g);
    // добавить создателя как участника
    if (!groupMemberRepository.existsByGroupAndUser(g, creator)) {
      groupMemberRepository.save(GroupMember.builder().group(g).user(creator).build());
    }
    return toDto(g);
  }

  @Transactional(readOnly = true)
  public List<GroupDto> findAll() {
    return groupRepository.findAll().stream().map(this::toDto).toList();
  }

  public GroupDto update(Long id, GroupDto dto) {
    Group g = groupRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    if (dto.getName() != null) g.setName(dto.getName());
    if (dto.getCreatorId() != null) g.setCreator(userService.getEntity(dto.getCreatorId()));
    return toDto(g);
  }

  public void delete(Long id) { groupRepository.deleteById(id); }

  @Transactional(readOnly = true)
  public Group getEntity(Long id) {
    return groupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Group not found"));
  }

  public void addMember(Long groupId, Long userId) {
    Group g = getEntity(groupId);
    User u = userService.getEntity(userId);
    if (!groupMemberRepository.existsByGroupAndUser(g, u)) {
      groupMemberRepository.save(GroupMember.builder().group(g).user(u).build());
    }
  }

  public void removeMember(Long groupId, Long userId) {
    groupMemberRepository.findByGroupIdAndUserId(groupId, userId)
        .ifPresent(groupMemberRepository::delete);
  }

  public boolean isMember(Long groupId, Long userId) {
    return groupMemberRepository.findByGroupIdAndUserId(groupId, userId).isPresent();
  }

  private GroupDto toDto(Group g) {
    return GroupDto.builder().id(g.getId()).name(g.getName()).creatorId(g.getCreator().getId()).build();
  }
}
