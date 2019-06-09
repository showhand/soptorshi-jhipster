import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FineAdvanceLoanManagementComponent,
    FineAdvanceLoanManagementDetailComponent,
    FineAdvanceLoanManagementUpdateComponent,
    FineAdvanceLoanManagementDeletePopupComponent,
    FineAdvanceLoanManagementDeleteDialogComponent,
    fineAdvanceLoanManagementRoute,
    fineAdvanceLoanManagementPopupRoute
} from './';
import { SoptorshiEmployeeModule } from 'app/entities/employee/employee.module';
import { SoptorshiFineModule } from 'app/entities/fine/fine.module';

const ENTITY_STATES = [...fineAdvanceLoanManagementRoute, ...fineAdvanceLoanManagementPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FineAdvanceLoanManagementComponent,
        FineAdvanceLoanManagementDetailComponent,
        FineAdvanceLoanManagementUpdateComponent,
        FineAdvanceLoanManagementDeleteDialogComponent,
        FineAdvanceLoanManagementDeletePopupComponent
    ],
    entryComponents: [
        FineAdvanceLoanManagementComponent,
        FineAdvanceLoanManagementUpdateComponent,
        FineAdvanceLoanManagementDeleteDialogComponent,
        FineAdvanceLoanManagementDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFineAdvanceLoanManagementModule {}
