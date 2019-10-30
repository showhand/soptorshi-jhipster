import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockInProcessDeleteDialogExtendedComponent,
    StockInProcessDeletePopupComponentExtended,
    StockInProcessDetailExtendedComponent,
    StockInProcessExtendedComponent,
    stockInProcessExtendedRoute,
    stockInProcessPopupRouteExtended,
    StockInProcessUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockInProcessExtendedRoute, ...stockInProcessPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockInProcessExtendedComponent,
        StockInProcessDetailExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupComponentExtended
    ],
    entryComponents: [
        StockInProcessExtendedComponent,
        StockInProcessUpdateExtendedComponent,
        StockInProcessDeleteDialogExtendedComponent,
        StockInProcessDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockInProcessModuleExtended {}
