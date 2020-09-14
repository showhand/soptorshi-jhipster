import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveAttachmentDeleteDialogExtendedComponent,
    LeaveAttachmentDeletePopupExtendedComponent,
    LeaveAttachmentDetailExtendedComponent,
    LeaveAttachmentExtendedComponent,
    leaveAttachmentExtendedRoute,
    leaveAttachmentPopupExtendedRoute,
    LeaveAttachmentUpdateExtendedComponent,
    OthersLeaveAttachmentUpdateComponent
} from './';
import { OthersLeaveAttachmentComponent } from 'app/entities/leave-attachment-extended/others-leave-attachment.component';

const ENTITY_STATES = [...leaveAttachmentExtendedRoute, ...leaveAttachmentPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveAttachmentExtendedComponent,
        LeaveAttachmentDetailExtendedComponent,
        LeaveAttachmentUpdateExtendedComponent,
        LeaveAttachmentDeleteDialogExtendedComponent,
        LeaveAttachmentDeletePopupExtendedComponent,
        OthersLeaveAttachmentComponent,
        OthersLeaveAttachmentUpdateComponent
    ],
    entryComponents: [
        LeaveAttachmentExtendedComponent,
        LeaveAttachmentUpdateExtendedComponent,
        LeaveAttachmentDeleteDialogExtendedComponent,
        LeaveAttachmentDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveAttachmentExtendedModule {}
