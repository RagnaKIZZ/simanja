<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pengaduan extends MX_Controller {

	public function __construct() 
	{
        parent::__construct();
		
		//$this->auth->adminRestrict();
		//$this->load->library('datatables');
	}
	
	function index()
	{
		$token_data = $thus->auth->tokenAuth($this->input->post('token'));
		$id_user = $token_data['id_user'];
		$hak_akses = $token_data['hak_akses'];
		//$this->auth->adminRestrict();
		if($hak_akses == 1)
		{
			$data_pengaduan = $this->datamapper->query2("SELECT tb_pengaduan.* FROM tb_pengaduan");
		}
		
		if($hak_akses == 2)
		{
			$data_pengaduan = $this->datamapper->query2("SELECT tb_pengaduan.* FROM tb_pengaduan WHERE tb_pengaduan.id_user = '$id_user' ");
		}
		
		
		/*foreach($data_pengaduan as $row_att)
		{
			$id_pengaduan = $row_att->id_pengaduan;
			$data_att = $this->datamapper->query2("SELECT tb_pengaduan_attachments.* FROM tb_pengaduan_attachments WHERE tb_pengaduan_attachments.id_pengaduan = '$id_pengaduan'");
			$data_rep = $this->datamapper->query2("SELECT tb_pengaduan_reply.* FROM tb_pengaduan_reply WHERE tb_pengaduan_reply.id_pengaduan = '$id_pengaduan'");
			//$att[] = array($data_att);
			
		};*/
		$output = array(
                'meta' => array(
                                    "code" => 200,
                                    "message" => "Berhasil"
                                ),
                'data' => array('data_pengaduan' => $data_pengaduan,
								)
            );
        print_r(json_encode($output));	
	}
	
	function inputPengaduan()
	{
//		$token 			= $this->input->post('token');
//		$token_data 	= $this->auth->tokenAuth($token);
		$id_pengaduan 	= $this->simanjalibs->getIdPengaduan();
		$judul 			= $this->input->post('judul');
		$pesan 			= $this->input->post('pesan');
		$alamat 		= $this->input->post('alamat');
//		$id_user 		= $token_data['id_user'];
		$id_user 		= $this->input->post('id_user');
		$foto 			= $this->input->post('foto');
		$lat 			= $this->input->post('lat');
		$lng 			= $this->input->post('lng');
		$kategori 		= $this->input->post('kategori');
//		$koordinat 		= $this->input->post('koordinat');
		$tgl_input 		= date("Y-m-d H:i:s");
		$tgl_update 	= date("Y-m-d H:i:s");
		
		if(empty($judul) || empty($pesan) || empty($alamat) || empty($kategori)){
			$output = array(
						'meta' => array(
											"code" 		=> 201,
											"message" 	=> "Lengkapi Data Pengaduan Anda!"
										)
					);
				print_r(json_encode($output));
			return($output);
		}
		
		if(!empty($_FILES['foto']['name']))
			{
				$do_upload1 = $this->utilities->uploadGambar('foto', 'assets/images/pengaduan', 'jpg', 512000, $this->utilities->keyHash());
				if($do_upload1['status'] == FALSE)
					{
						$error_message = $do_upload1['message'] ;
						$output = array(
								'meta' => array(
													"code" => 201,
													"message" => 'Gagal Input data Pengaduan, Error Message : '.$error_message.''
												),
								'data' => ''
						);
					print_r(json_encode($output));
					}
					else
					{
						$upload1 = $do_upload1['filename'];
					}
			}else
				{
					if(!empty($id_pengaduan))
					{	
						$upload1 = $this->datamapper->rowData('tb_pengaduan', 'foto', 'id_pengaduan', $id_pengaduan);
					}else
						{
							$upload1 = '';
						}
				}
				

				$data = array(
						'id_pengaduan'	=> $id_pengaduan,
						'judul' 		=> $judul,
						'pesan' 		=> $pesan,
						'id_user' 		=> $id_user,
						'foto' 			=> $upload1,
						'lat' 			=> $lat,
						'lng' 			=> $lng,
						'alamat' 		=> $alamat,
//						'koordinat'		=> $koordinat,
						'kategori' 		=> $kategori,
						'tgl_input' 	=> $tgl_input,
						'tgl_update' 	=> $tgl_update
				);
				$this->datamapper->insert('tb_pengaduan', $data);
				
				$output = array(
						'meta' => array(
											"code" 		=> 200,
											"message" 	=> "Data Pengaduan Berhasil Dikirim"
										)
//										,
//						'data' => array('data_pengaduan'=> $data_pengaduan
//										)
					);
				print_r(json_encode($output));
		}
	
	function detail()
	{
		$id_pengaduan = $this->input->post('id_pengaduan');
		$token_data = $this->auth->tokenAuth($this->input->post('token'));
		if(!empty($id_pengaduan))
		{
			$data_pengaduan = $this->datamapper->query2("SELECT tb_pengaduan.* FROM tb_pengaduan WHERE tb_pengaduan.id_pengaduan = '$id_pengaduan' ");
			foreach($data_pengaduan as $row_att)
			{
				$id_pengaduan = $row_att->id_pengaduan;
				$data_rep = $this->datamapper->query2("SELECT tb_pengaduan_reply.* FROM tb_pengaduan_reply WHERE tb_pengaduan_reply.id_pengaduan = '$id_pengaduan'");
				//$att[] = array($data_att);

			};
			$output = array(
					'meta' => array(
										"code" => 200,
										"message" => "Berhasil"
									),
					'data' => array('data_pengaduan' => $data_pengaduan,
									'reply' =>$data_rep
									)
				);
			print_r(json_encode($output));
		}
		else
		{
			redirect(''.$_SERVER['HTTP_REFERRER'].'');
		}
			
	}
	
//	function save()
//	{
//		$token = $this->input->post('token');
//		$token_data = $thus->auth->tokenAuth($token);
//		$id_pengaduan = $this->input->post('id_pengaduan');
//		$judul = $this->input->post('judul');
//		$pesan = $this->input->post('pesan');
//		$status = $this->input->post('status');
//		$alamat = $this->input->post('alamat');
//		$id_user = $token_data['id_user'];
//		$kategori = $this->input->post('kategori');
//		$koordinat = $this->input->post('koordinat');
//		$tgl_input = date("Y-m-d H:i:s");
//		
//		if(!empty($_FILES['foto']['name']))
//			{
//				$do_upload1 = $this->utilities->uploadGambar('foto', 'assets/files/pengadian', 'jpg', 512000, $this->utilities->keyHash());
//				if($do_upload1['status'] == FALSE)
//					{
//						$error_message = $do_upload1['message'] ;
//						$output = array(
//								'meta' => array(
//													"code" => 201,
//													"message" => 'Gagal Input data Pengaduan, Error Message : '.$error_message.''
//												),
//								'data' => ''
//						);
//					print_r(json_encode($output));
//					}
//					else
//					{
//						$upload1 = $do_upload1['filename'];
//					}
//			}else
//				{
//					if(!empty($id_pengaduan))
//					{	
//						$upload1 = $this->datamapper->rowData('tb_pengaduan', 'foto', 'id_pengaduan', $id_pengaduan);
//					}else
//						{
//							$upload1 = '';
//						}
//				}
//			
//		//(END) UNTUK UPLOAD FILE ATAU GAMBAR
//
//
//			if(!empty($id_pengaduan))
//			{
//				$data = array(
//						'judul' => $judul,
//						'pesan' => $pesan,
//						'id_user' => $id_user,
//						'alamat' => $alamat,
//						'kategori' => $kategori,
//						'koordinat' => $panjang_jln,
//						'foto' => $jenis,
//						'tgl_input' => $tgl_input
//				);
//				$this->datamapper->update('tb_jalan', 'id_jalan', $id_jalan, $data);
//				$this->utilities->notification('success', 'Selamat, Data anda berhasil di Update.');
//				redirect(''.$_SERVER['HTTP_REFERER'].'');
//			}
//			else
//			{
//				$data = array(
//						'id_kecamatan' => $id_kecamatan,
//						'no_ruas' => $no_ruas,
//						'nama_jln' => $nama_ljn,
//						'ujung_jln' => $ujung_ljn,
//						'pangkal_jln' => $pangkal_ljn,
//						'panjang_jln' => $panjang_jln,
//						'jenis' => $jenis,
//						'ket' => $ket,
//						'type' => $type,
//						'lat' => $lat,
//						'lng' => $lng,
//						'kondisi' => $kondisi,
//						'tgl_input' => $tgl_input
//
//				);
//				$this->datamapper->insert('tb_jalan', $data);
//				$this->utilities->notification('success', 'Selamat, Data anda berhasil di Input.');
//				redirect(''.$_SERVER['HTTP_REFERER'].'');	
//			}
//
//	}
	
}