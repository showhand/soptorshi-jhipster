import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SpecialAllowanceTimeLineExtendedComponent,
    SpecialAllowanceTimeLineExtendedDetailComponent,
    SpecialAllowanceTimeLineExtendedUpdateComponent,
    SpecialAllowanceTimeLineExtendedDeleteDialogComponent,
    specialAllowanceTimeLineExtendedRoute,
    SpecialAllowanceTimeLineExtendedDeletePopupComponent,
    specialAllowanceTimeLineExtendedPopupRoute
} from './';
import {
    SpecialAllowanceTimeLineComponent,
    SpecialAllowanceTimeLineDeleteDialogComponent,
    SpecialAllowanceTimeLineDeletePopupComponent,
    SpecialAllowanceTimeLineDetailComponent,
    specialAllowanceTimeLinePopupRoute,
    SpecialAllowanceTimeLineUpdateComponent
} from 'app/entities/special-allowance-time-line';

const ENTITY_STATES = [...specialAllowanceTimeLineExtendedRoute, ...specialAllowanceTimeLineExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SpecialAllowanceTimeLineComponent,
        SpecialAllowanceTimeLineDetailComponent,
        SpecialAllowanceTimeLineUpdateComponent,
        SpecialAllowanceTimeLineDeleteDialogComponent,
        SpecialAllowanceTimeLineDeletePopupComponent,
        SpecialAllowanceTimeLineExtendedComponent,
        SpecialAllowanceTimeLineExtendedDetailComponent,
        SpecialAllowanceTimeLineExtendedUpdateComponent,
        SpecialAllowanceTimeLineExtendedDeleteDialogComponent,
        SpecialAllowanceTimeLineExtendedDeletePopupComponent
    ],
    entryComponents: [
        SpecialAllowanceTimeLineExtendedComponent,
        SpecialAllowanceTimeLineExtendedUpdateComponent,
        SpecialAllowanceTimeLineExtendedDeleteDialogComponent,
        SpecialAllowanceTimeLineExtendedDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSpecialAllowanceTimeLineModule {}
