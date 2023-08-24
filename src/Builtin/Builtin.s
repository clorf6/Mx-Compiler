	.text
	.globl	print                           # -- Begin function print
	.p2align	2
print:                                  # @print
# %bb.0:
	lui	a1, %hi(.L.str)
	addi	a1, a1, %lo(.L.str)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end0:
                                        # -- End function
	.globl	println                         # -- Begin function println
	.p2align	2
println:                                # @println
# %bb.0:
	tail	puts
.Lfunc_end1:
                                        # -- End function
	.globl	printInt                        # -- Begin function printInt
	.p2align	2
printInt:                               # @printInt
# %bb.0:
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end2:
                                        # -- End function
	.globl	printlnInt                      # -- Begin function printlnInt
	.p2align	2
printlnInt:                             # @printlnInt
# %bb.0:
	lui	a1, %hi(.L.str.3)
	addi	a1, a1, %lo(.L.str.3)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end3:
                                        # -- End function
	.globl	getString                       # -- Begin function getString
	.p2align	2
getString:                              # @getString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	li	a0, 256
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	__isoc99_scanf
	mv	a0, s0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end4:
                                        # -- End function
	.globl	getInt                          # -- Begin function getInt
	.p2align	2
getInt:                                 # @getInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	__isoc99_scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end5:
                                        # -- End function
	.globl	toString                        # -- Begin function toString
	.p2align	2
toString:                               # @toString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	mv	s0, a0
	li	a0, 256
	call	malloc
	mv	s1, a0
	lui	a0, %hi(.L.str.2)
	addi	a1, a0, %lo(.L.str.2)
	mv	a0, s1
	mv	a2, s0
	call	sprintf
	mv	a0, s1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end6:
                                        # -- End function
	.globl	_array.size                     # -- Begin function _array.size
	.p2align	2
_array.size:                            # @_array.size
# %bb.0:
	lw	a0, -4(a0)
	ret
.Lfunc_end7:
                                        # -- End function
	.globl	string.length                   # -- Begin function string.length
	.p2align	2
string.length:                          # @string.length
# %bb.0:
	tail	strlen
.Lfunc_end8:
                                        # -- End function
	.globl	string.substring                # -- Begin function string.substring
	.p2align	2
string.substring:                       # @string.substring
# %bb.0:
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	sw	s1, 20(sp)                      # 4-byte Folded Spill
	sw	s2, 16(sp)                      # 4-byte Folded Spill
	sw	s3, 12(sp)                      # 4-byte Folded Spill
	sw	s4, 8(sp)                       # 4-byte Folded Spill
	mv	s4, a2
	mv	s2, a1
	mv	s3, a0
	sub	s0, a2, a1
	addi	a0, s0, 1
	call	malloc
	mv	s1, a0
	bge	s2, s4, .LBB9_2
# %bb.1:
	add	a1, s3, s2
	mv	a0, s1
	mv	a2, s0
	call	memcpy
.LBB9_2:
	add	a0, s1, s0
	sb	zero, 0(a0)
	mv	a0, s1
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	lw	s1, 20(sp)                      # 4-byte Folded Reload
	lw	s2, 16(sp)                      # 4-byte Folded Reload
	lw	s3, 12(sp)                      # 4-byte Folded Reload
	lw	s4, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end9:
                                        # -- End function
	.globl	string.parseInt                 # -- Begin function string.parseInt
	.p2align	2
string.parseInt:                        # @string.parseInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	addi	a2, sp, 8
	call	__isoc99_sscanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end10:
                                        # -- End function
	.globl	string.ord                      # -- Begin function string.ord
	.p2align	2
string.ord:                             # @string.ord
# %bb.0:
	add	a0, a0, a1
	lb	a0, 0(a0)
	ret
.Lfunc_end11:
                                        # -- End function
	.globl	string.add                      # -- Begin function string.add
	.p2align	2
string.add:                             # @string.add
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	sw	s2, 0(sp)                       # 4-byte Folded Spill
	mv	s0, a1
	mv	s1, a0
	call	strlen
	mv	s2, a0
	mv	a0, s0
	call	strlen
	add	a0, s2, a0
	addi	a0, a0, 1
	call	malloc
	mv	s2, a0
	mv	a1, s1
	call	strcpy
	mv	a0, s2
	mv	a1, s0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	lw	s2, 0(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	tail	strcat
.Lfunc_end12:
                                        # -- End function
	.globl	string.le                       # -- Begin function string.le
	.p2align	2
string.le:                              # @string.le
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end13:
                                        # -- End function
	.globl	string.leq                      # -- Begin function string.leq
	.p2align	2
string.leq:                             # @string.leq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	slti	a0, a0, 1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end14:
                                        # -- End function
	.globl	string.ge                       # -- Begin function string.ge
	.p2align	2
string.ge:                              # @string.ge
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	sgtz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end15:
                                        # -- End function
	.globl	string.geq                      # -- Begin function string.geq
	.p2align	2
string.geq:                             # @string.geq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end16:
                                        # -- End function
	.globl	string.eq                       # -- Begin function string.eq
	.p2align	2
string.eq:                              # @string.eq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	seqz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end17:
                                        # -- End function
	.globl	string.neq                      # -- Begin function string.neq
	.p2align	2
string.neq:                             # @string.neq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	snez	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end18:
                                        # -- End function
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"

.L.str.2:
	.asciz	"%d"

.L.str.3:
	.asciz	"%d\n"
