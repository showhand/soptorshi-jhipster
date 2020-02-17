import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationDeleteDialogExtendedComponent,
    LeaveApplicationDeletePopupExtendedComponent,
    LeaveApplicationDetailExtendedComponent,
    LeaveApplicationExtendedComponent,
    leaveApplicationExtendedRoute,
    leaveApplicationPopupExtendedRoute,
    LeaveApplicationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...leaveApplicationExtendedRoute, ...leaveApplicationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationDetailExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent
    ],
    entryComponents: [
        LeaveApplicationExtendedComponent,
        LeaveApplicationUpdateExtendedComponent,
        LeaveApplicationDeleteDialogExtendedComponent,
        LeaveApplicationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationExtendedModule {}
