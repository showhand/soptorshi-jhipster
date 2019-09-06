/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SystemGroupMapDetailComponent } from 'app/entities/system-group-map/system-group-map-detail.component';
import { SystemGroupMap } from 'app/shared/model/system-group-map.model';

describe('Component Tests', () => {
    describe('SystemGroupMap Management Detail Component', () => {
        let comp: SystemGroupMapDetailComponent;
        let fixture: ComponentFixture<SystemGroupMapDetailComponent>;
        const route = ({ data: of({ systemGroupMap: new SystemGroupMap(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SystemGroupMapDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SystemGroupMapDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemGroupMapDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.systemGroupMap).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
