import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    LeaveApplicationComponent,
    LeaveApplicationDeleteDialogComponent,
    LeaveApplicationDeletePopupComponent,
    LeaveApplicationDetailComponent,
    leaveApplicationPopupRoute,
    leaveApplicationRoute,
    LeaveApplicationUpdateComponent
} from './';

const ENTITY_STATES = [...leaveApplicationRoute, ...leaveApplicationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LeaveApplicationComponent,
        LeaveApplicationDetailComponent,
        LeaveApplicationUpdateComponent,
        LeaveApplicationDeleteDialogComponent,
        LeaveApplicationDeletePopupComponent
    ],
    entryComponents: [
        LeaveApplicationComponent,
        LeaveApplicationUpdateComponent,
        LeaveApplicationDeleteDialogComponent,
        LeaveApplicationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiLeaveApplicationModule {}
