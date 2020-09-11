package com.kanata.message.controller;

import com.kanata.core.entity.LikeDynamicEntity;
import com.kanata.feign.api.user.IUserInfoFeignClient;
import com.kanata.feign.dto.UserInfoDto;
import com.kanata.message.controller.api.dynamic.ResponseLikeGet;
import com.kanata.message.service.LikeDynamicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "APP用户点赞动态")
@RequestMapping("/app/like/{userId}/dynamic")
public class LikeDynamicController {

    @Autowired
    private LikeDynamicService likeDynamicService;

    @Autowired
    private IUserInfoFeignClient userInfoFeignClient;

    @PostMapping("/create/{dynamicId}")
    @ApiOperation("用户点赞或取消点赞")
    public void create(@PathVariable Integer userId, @PathVariable Integer dynamicId){
        likeDynamicService.LikeDynamic(userId,dynamicId);
    }

    @GetMapping("/query/{dynamicId}")
    @ApiOperation("用户分页查询点赞")
    public ResponseLikeGet queryLike(@ApiParam("页数") @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                     @ApiParam("每页个数") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     @PathVariable Integer dynamicId){
        Page<LikeDynamicEntity> pageData = likeDynamicService.query(dynamicId,pageNo,pageSize);
        ResponseLikeGet response = new ResponseLikeGet();
        List<ResponseLikeGet.LikeDynamic> likeDynamics = new ArrayList<>();
        likeDynamics = pageData.stream().map(e->{
            ResponseLikeGet.LikeDynamic likeDynamic = new ResponseLikeGet.LikeDynamic();
            BeanUtils.copyProperties(e,likeDynamic);
            UserInfoDto userInfoDto = userInfoFeignClient.findUserInfo(e.getUserId());
            likeDynamic.setAvatar(userInfoDto.getAvatar());
            likeDynamic.setUserName(userInfoDto.getNickName());
            return likeDynamic;
        }).collect(Collectors.toList());
        response.setData(likeDynamics);
        response.setTotal((int)pageData.getTotalElements());
        return response;
    }

    @ApiOperation(value = "判断用户是否点赞动态")
    @GetMapping(value = "/{dynamicId}/state")
    public Boolean find(@PathVariable Integer dynamicId, @PathVariable Integer userId){
        return likeDynamicService.queryState(userId,dynamicId).size()>0;
    }

}
