/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FineManagementDetailComponent } from 'app/entities/fine-management/fine-management-detail.component';
import { FineManagement } from 'app/shared/model/fine-management.model';

describe('Component Tests', () => {
    describe('FineManagement Management Detail Component', () => {
        let comp: FineManagementDetailComponent;
        let fixture: ComponentFixture<FineManagementDetailComponent>;
        const route = ({ data: of({ fineManagement: new FineManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FineManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FineManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fineManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
