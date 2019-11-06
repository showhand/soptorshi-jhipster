/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaManagerDetailComponent } from 'app/entities/supply-area-manager/supply-area-manager-detail.component';
import { SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

describe('Component Tests', () => {
    describe('SupplyAreaManager Management Detail Component', () => {
        let comp: SupplyAreaManagerDetailComponent;
        let fixture: ComponentFixture<SupplyAreaManagerDetailComponent>;
        const route = ({ data: of({ supplyAreaManager: new SupplyAreaManager(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaManagerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyAreaManagerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyAreaManagerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyAreaManager).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
