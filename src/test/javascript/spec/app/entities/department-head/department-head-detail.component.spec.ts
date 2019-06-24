/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepartmentHeadDetailComponent } from 'app/entities/department-head/department-head-detail.component';
import { DepartmentHead } from 'app/shared/model/department-head.model';

describe('Component Tests', () => {
    describe('DepartmentHead Management Detail Component', () => {
        let comp: DepartmentHeadDetailComponent;
        let fixture: ComponentFixture<DepartmentHeadDetailComponent>;
        const route = ({ data: of({ departmentHead: new DepartmentHead(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepartmentHeadDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepartmentHeadDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepartmentHeadDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.departmentHead).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
