import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SpecialAllowanceTimeLineComponent,
    SpecialAllowanceTimeLineDetailComponent,
    SpecialAllowanceTimeLineUpdateComponent,
    SpecialAllowanceTimeLineDeletePopupComponent,
    SpecialAllowanceTimeLineDeleteDialogComponent,
    specialAllowanceTimeLineRoute,
    specialAllowanceTimeLinePopupRoute
} from './';

const ENTITY_STATES = [...specialAllowanceTimeLineRoute, ...specialAllowanceTimeLinePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        SpecialAllowanceTimeLineComponent,
        SpecialAllowanceTimeLineDetailComponent,
        SpecialAllowanceTimeLineUpdateComponent,
        SpecialAllowanceTimeLineDeleteDialogComponent,
        SpecialAllowanceTimeLineDeletePopupComponent
    ],
    entryComponents: [
        SpecialAllowanceTimeLineComponent,
        SpecialAllowanceTimeLineUpdateComponent,
        SpecialAllowanceTimeLineDeleteDialogComponent,
        SpecialAllowanceTimeLineDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSpecialAllowanceTimeLineModule {}
