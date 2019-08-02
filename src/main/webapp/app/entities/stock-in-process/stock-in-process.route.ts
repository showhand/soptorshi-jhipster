import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessService } from './stock-in-process.service';
import { StockInProcessComponent } from './stock-in-process.component';
import { StockInProcessDetailComponent } from './stock-in-process-detail.component';
import { StockInProcessUpdateComponent } from './stock-in-process-update.component';
import { StockInProcessDeletePopupComponent } from './stock-in-process-delete-dialog.component';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';

@Injectable({ providedIn: 'root' })
export class StockInProcessResolve implements Resolve<IStockInProcess> {
    constructor(private service: StockInProcessService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockInProcess> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockInProcess>) => response.ok),
                map((stockInProcess: HttpResponse<StockInProcess>) => stockInProcess.body)
            );
        }
        return of(new StockInProcess());
    }
}

export const stockInProcessRoute: Routes = [
    {
        path: '',
        component: StockInProcessComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInProcessDetailComponent,
        resolve: {
            stockInProcess: StockInProcessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInProcessUpdateComponent,
        resolve: {
            stockInProcess: StockInProcessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInProcessUpdateComponent,
        resolve: {
            stockInProcess: StockInProcessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInProcessPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockInProcessDeletePopupComponent,
        resolve: {
            stockInProcess: StockInProcessResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
