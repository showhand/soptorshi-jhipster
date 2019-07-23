import {
    DesignationWiseAllowanceComponent,
    DesignationWiseAllowanceDeleteDialogComponent,
    DesignationWiseAllowanceDeletePopupComponent,
    DesignationWiseAllowanceDetailComponent,
    designationWiseAllowancePopupRoute,
    designationWiseAllowanceRoute,
    DesignationWiseAllowanceUpdateComponent
} from 'app/entities/designation-wise-allowance';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SoptorshiSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { DesignationWiseAllowanceExtendedComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended.component';
import { DesignationWiseAllowanceExtendedDetailsComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended-details.component';
import { DesignationWiseAllowanceExtendedUpdateComponent } from 'app/entities/designation-wise-allowance-extended/designation-wise-allowance-extended-update.component';

const ENTITY_STATES = [...designationWiseAllowanceRoute, ...designationWiseAllowancePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DesignationWiseAllowanceComponent,
        DesignationWiseAllowanceExtendedComponent,
        DesignationWiseAllowanceDetailComponent,
        DesignationWiseAllowanceExtendedDetailsComponent,
        DesignationWiseAllowanceUpdateComponent,
        DesignationWiseAllowanceExtendedUpdateComponent,
        DesignationWiseAllowanceDeleteDialogComponent,
        DesignationWiseAllowanceDeletePopupComponent
    ],
    entryComponents: [
        DesignationWiseAllowanceComponent,
        DesignationWiseAllowanceExtendedComponent,
        DesignationWiseAllowanceDetailComponent,
        DesignationWiseAllowanceExtendedDetailsComponent,
        DesignationWiseAllowanceUpdateComponent,
        DesignationWiseAllowanceExtendedUpdateComponent,
        DesignationWiseAllowanceDeleteDialogComponent,
        DesignationWiseAllowanceDeletePopupComponent
    ],
    exports: [DesignationWiseAllowanceComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDesignationWiseAllowanceExtendedModule {}
