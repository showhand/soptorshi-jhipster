import { Pipe, PipeTransform } from '@angular/core';
import { LeaveAttachment } from 'app/shared/model/leave-attachment.model';

@Pipe({ name: 'leaveAttachmentFilter' })
export class LeaveAttachmentFilterPipe implements PipeTransform {
    transform(value: number, args: LeaveAttachment[]): LeaveAttachment[] {
        let attachments: LeaveAttachment[] = [];
        if (args !== null && args !== undefined) {
            for (let i = 0; i < args.length; i++) {
                if (value === args[i].leaveApplicationId) {
                    attachments.push(args[i]);
                }
            }
        }
        return attachments;
    }
}
