import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ProvidentFundComponent,
    ProvidentFundDetailComponent,
    ProvidentFundUpdateComponent,
    ProvidentFundDeletePopupComponent,
    ProvidentFundDeleteDialogComponent,
    providentFundRoute,
    providentFundPopupRoute
} from './';

const ENTITY_STATES = [...providentFundRoute, ...providentFundPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProvidentFundComponent,
        ProvidentFundDetailComponent,
        ProvidentFundUpdateComponent,
        ProvidentFundDeleteDialogComponent,
        ProvidentFundDeletePopupComponent
    ],
    entryComponents: [
        ProvidentFundComponent,
        ProvidentFundUpdateComponent,
        ProvidentFundDeleteDialogComponent,
        ProvidentFundDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProvidentFundModule {}
