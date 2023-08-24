; ModuleID = 'Builtin.c'
source_filename = "Builtin.c"
target datalayout = "e-m:e-p:32:32-p270:32:32-p271:32:32-p272:64:64-f64:32:64-f80:32-n8:16:32-S128"
target triple = "riscv-pc-linux-gnu"

@.str = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str.2 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

; Function Attrs: nofree nounwind uwtable
define dso_local void @print(ptr noundef %0) local_unnamed_addr #0 {
  %2 = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str, ptr noundef %0)
  ret void
}

; Function Attrs: nofree nounwind
declare noundef i32 @printf(ptr nocapture noundef readonly, ...) local_unnamed_addr #1

; Function Attrs: nofree nounwind uwtable
define dso_local void @println(ptr nocapture noundef readonly %0) local_unnamed_addr #0 {
  %2 = tail call i32 @puts(ptr nonnull dereferenceable(1) %0)
  ret void
}

; Function Attrs: nofree nounwind uwtable
define dso_local void @printInt(i32 noundef %0) local_unnamed_addr #0 {
  %2 = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str.2, i32 noundef %0)
  ret void
}

; Function Attrs: nofree nounwind uwtable
define dso_local void @printlnInt(i32 noundef %0) local_unnamed_addr #0 {
  %2 = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str.3, i32 noundef %0)
  ret void
}

; Function Attrs: nofree nounwind uwtable
define dso_local ptr @getString() local_unnamed_addr #0 {
  %1 = tail call noalias dereferenceable_or_null(256) ptr @malloc(i32 noundef 256) #11
  %2 = tail call i32 (ptr, ...) @__isoc99_scanf(ptr noundef nonnull @.str, ptr noundef %1)
  ret ptr %1
}

; Function Attrs: argmemonly mustprogress nocallback nofree nosync nounwind willreturn
declare void @llvm.lifetime.start.p0(i64 immarg, ptr nocapture) #2

; Function Attrs: inaccessiblememonly mustprogress nofree nounwind willreturn allockind("alloc,uninitialized") allocsize(0)
declare noalias noundef ptr @malloc(i32 noundef) local_unnamed_addr #3

; Function Attrs: nofree nounwind
declare noundef i32 @__isoc99_scanf(ptr nocapture noundef readonly, ...) local_unnamed_addr #1

; Function Attrs: argmemonly mustprogress nocallback nofree nosync nounwind willreturn
declare void @llvm.lifetime.end.p0(i64 immarg, ptr nocapture) #2

; Function Attrs: nofree nounwind uwtable
define dso_local i32 @getInt() local_unnamed_addr #0 {
  %1 = alloca i32, align 4
  call void @llvm.lifetime.start.p0(i64 4, ptr nonnull %1) #12
  %2 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef nonnull @.str.2, ptr noundef nonnull %1)
  %3 = load i32, ptr %1, align 4, !tbaa !6
  call void @llvm.lifetime.end.p0(i64 4, ptr nonnull %1) #12
  ret i32 %3
}

; Function Attrs: nofree nounwind uwtable
define dso_local noalias ptr @toString(i32 noundef %0) local_unnamed_addr #0 {
  %2 = tail call noalias dereferenceable_or_null(256) ptr @malloc(i32 noundef 256) #11
  %3 = tail call i32 (ptr, ptr, ...) @sprintf(ptr noundef nonnull dereferenceable(1) %2, ptr noundef nonnull @.str.2, i32 noundef %0) #12
  ret ptr %2
}

; Function Attrs: nofree nounwind
declare noundef i32 @sprintf(ptr noalias nocapture noundef writeonly, ptr nocapture noundef readonly, ...) local_unnamed_addr #1

; Function Attrs: argmemonly mustprogress nofree norecurse nosync nounwind readonly willreturn uwtable
define dso_local i32 @_array.size(ptr nocapture noundef readonly %0) local_unnamed_addr #4 {
  %2 = getelementptr inbounds i32, ptr %0, i32 -1
  %3 = load i32, ptr %2, align 4, !tbaa !6
  ret i32 %3
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local i32 @string.length(ptr nocapture noundef readonly %0) local_unnamed_addr #5 {
  %2 = tail call i32 @strlen(ptr noundef nonnull dereferenceable(1) %0) #13
  ret i32 %2
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn
declare i32 @strlen(ptr nocapture noundef) local_unnamed_addr #6

; Function Attrs: nofree nounwind uwtable
define dso_local noalias ptr @string.substring(ptr nocapture noundef readonly %0, i32 noundef %1, i32 noundef %2) local_unnamed_addr #0 {
  %4 = sub i32 %2, %1
  %5 = add nsw i32 %4, 1
  %6 = tail call noalias ptr @malloc(i32 noundef %5) #11
  %7 = icmp sgt i32 %2, %1
  br i1 %7, label %8, label %10

8:                                                ; preds = %3
  %9 = getelementptr i8, ptr %0, i32 %1
  tail call void @llvm.memcpy.p0.p0.i32(ptr align 1 %6, ptr align 1 %9, i32 %4, i1 false), !tbaa !10
  br label %10

10:                                               ; preds = %8, %3
  %11 = getelementptr inbounds i8, ptr %6, i32 %4
  store i8 0, ptr %11, align 1, !tbaa !10
  ret ptr %6
}

; Function Attrs: nofree nounwind uwtable
define dso_local i32 @string.parseInt(ptr nocapture noundef readonly %0) local_unnamed_addr #0 {
  %2 = alloca i32, align 4
  call void @llvm.lifetime.start.p0(i64 4, ptr nonnull %2) #12
  %3 = call i32 (ptr, ptr, ...) @__isoc99_sscanf(ptr noundef %0, ptr noundef nonnull @.str.2, ptr noundef nonnull %2) #12
  %4 = load i32, ptr %2, align 4, !tbaa !6
  call void @llvm.lifetime.end.p0(i64 4, ptr nonnull %2) #12
  ret i32 %4
}

; Function Attrs: nofree nounwind
declare noundef i32 @__isoc99_sscanf(ptr nocapture noundef readonly, ptr nocapture noundef readonly, ...) local_unnamed_addr #1

; Function Attrs: argmemonly mustprogress nofree norecurse nosync nounwind readonly willreturn uwtable
define dso_local i32 @string.ord(ptr nocapture noundef readonly %0, i32 noundef %1) local_unnamed_addr #4 {
  %3 = getelementptr inbounds i8, ptr %0, i32 %1
  %4 = load i8, ptr %3, align 1, !tbaa !10
  %5 = sext i8 %4 to i32
  ret i32 %5
}

; Function Attrs: mustprogress nofree nounwind willreturn uwtable
define dso_local ptr @string.add(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #7 {
  %3 = tail call i32 @strlen(ptr noundef nonnull dereferenceable(1) %0) #13
  %4 = tail call i32 @strlen(ptr noundef nonnull dereferenceable(1) %1) #13
  %5 = add i32 %3, 1
  %6 = add i32 %5, %4
  %7 = tail call noalias ptr @malloc(i32 noundef %6) #11
  %8 = tail call ptr @strcpy(ptr noundef nonnull dereferenceable(1) %7, ptr noundef nonnull dereferenceable(1) %0) #12
  %9 = tail call ptr @strcat(ptr noundef nonnull dereferenceable(1) %7, ptr noundef nonnull dereferenceable(1) %1) #12
  ret ptr %7
}

; Function Attrs: argmemonly mustprogress nofree nounwind willreturn
declare ptr @strcpy(ptr noalias noundef returned writeonly, ptr noalias nocapture noundef readonly) local_unnamed_addr #8

; Function Attrs: argmemonly mustprogress nofree nounwind willreturn
declare ptr @strcat(ptr noalias noundef returned, ptr noalias nocapture noundef readonly) local_unnamed_addr #8

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.le(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp slt i32 %3, 0
  ret i1 %4
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn
declare i32 @strcmp(ptr nocapture noundef, ptr nocapture noundef) local_unnamed_addr #6

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.leq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp slt i32 %3, 1
  ret i1 %4
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.ge(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp sgt i32 %3, 0
  ret i1 %4
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.geq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp sgt i32 %3, -1
  ret i1 %4
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.eq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp eq i32 %3, 0
  ret i1 %4
}

; Function Attrs: argmemonly mustprogress nofree nounwind readonly willreturn uwtable
define dso_local zeroext i1 @string.neq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1) local_unnamed_addr #5 {
  %3 = tail call i32 @strcmp(ptr noundef nonnull dereferenceable(1) %0, ptr noundef nonnull dereferenceable(1) %1) #13
  %4 = icmp ne i32 %3, 0
  ret i1 %4
}

; Function Attrs: nofree nounwind
declare noundef i32 @puts(ptr nocapture noundef readonly) local_unnamed_addr #9

; Function Attrs: argmemonly nocallback nofree nounwind willreturn
declare void @llvm.memcpy.p0.p0.i32(ptr noalias nocapture writeonly, ptr noalias nocapture readonly, i32, i1 immarg) #10

!llvm.module.flags = !{!0, !1, !2, !3, !4}
!llvm.ident = !{!5}

!0 = !{i32 1, !"NumRegisterParameters", i32 0}
!1 = !{i32 1, !"wchar_size", i32 4}
!2 = !{i32 7, !"PIC Level", i32 2}
!3 = !{i32 7, !"PIE Level", i32 2}
!4 = !{i32 7, !"uwtable", i32 2}
!5 = !{!"Ubuntu clang version 15.0.7"}
!6 = !{!7, !7, i64 0}
!7 = !{!"int", !8, i64 0}
!8 = !{!"omnipotent char", !9, i64 0}
!9 = !{!"Simple C/C++ TBAA"}
!10 = !{!8, !8, i64 0}
