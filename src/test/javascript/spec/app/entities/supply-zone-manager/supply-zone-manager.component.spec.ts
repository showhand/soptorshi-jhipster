/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneManagerComponent } from 'app/entities/supply-zone-manager/supply-zone-manager.component';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager/supply-zone-manager.service';
import { SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

describe('Component Tests', () => {
    describe('SupplyZoneManager Management Component', () => {
        let comp: SupplyZoneManagerComponent;
        let fixture: ComponentFixture<SupplyZoneManagerComponent>;
        let service: SupplyZoneManagerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneManagerComponent],
                providers: []
            })
                .overrideTemplate(SupplyZoneManagerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyZoneManagerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneManagerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SupplyZoneManager(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.supplyZoneManagers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
