import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DesignationWiseAllowanceComponent,
    DesignationWiseAllowanceDetailComponent,
    DesignationWiseAllowanceUpdateComponent,
    DesignationWiseAllowanceDeletePopupComponent,
    DesignationWiseAllowanceDeleteDialogComponent,
    designationWiseAllowanceRoute,
    designationWiseAllowancePopupRoute
} from './';

const ENTITY_STATES = [...designationWiseAllowanceRoute, ...designationWiseAllowancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        DesignationWiseAllowanceComponent,
        DesignationWiseAllowanceDetailComponent,
        DesignationWiseAllowanceUpdateComponent,
        DesignationWiseAllowanceDeleteDialogComponent,
        DesignationWiseAllowanceDeletePopupComponent
    ],
    entryComponents: [
        DesignationWiseAllowanceComponent,
        DesignationWiseAllowanceUpdateComponent,
        DesignationWiseAllowanceDeleteDialogComponent,
        DesignationWiseAllowanceDeletePopupComponent
    ],
    exports: [DesignationWiseAllowanceComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDesignationWiseAllowanceModule {}
