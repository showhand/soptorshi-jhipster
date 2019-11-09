import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplySalesRepresentative, SupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from './supply-sales-representative.service';
import { SupplySalesRepresentativeComponent } from './supply-sales-representative.component';
import { SupplySalesRepresentativeDetailComponent } from './supply-sales-representative-detail.component';
import { SupplySalesRepresentativeUpdateComponent } from './supply-sales-representative-update.component';
import { SupplySalesRepresentativeDeletePopupComponent } from './supply-sales-representative-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplySalesRepresentativeResolve implements Resolve<ISupplySalesRepresentative> {
    constructor(private service: SupplySalesRepresentativeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplySalesRepresentative> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplySalesRepresentative>) => response.ok),
                map((supplySalesRepresentative: HttpResponse<SupplySalesRepresentative>) => supplySalesRepresentative.body)
            );
        }
        return of(new SupplySalesRepresentative());
    }
}

export const supplySalesRepresentativeRoute: Routes = [
    {
        path: '',
        component: SupplySalesRepresentativeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplySalesRepresentativeDetailComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplySalesRepresentativeUpdateComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplySalesRepresentativeUpdateComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplySalesRepresentativePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplySalesRepresentativeDeletePopupComponent,
        resolve: {
            supplySalesRepresentative: SupplySalesRepresentativeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplySalesRepresentatives'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
